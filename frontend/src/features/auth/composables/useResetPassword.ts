// Composable for Reset Password endpoint
import { useMutation } from '@tanstack/vue-query'
import { useForm } from 'vee-validate'
import { useRouter } from 'vue-router'
import * as yup from 'yup'
import { apiClient } from '../../../shared/api/apiClient'
import type { ApiResponse, ResetPasswordRequest } from '../../../shared/types/types'
import type { Ref } from 'vue'

export const useResetPassword = (token: Ref<string> | string) => {
  const router = useRouter()

  // Validation schema
  const schema = yup.object({
    newPassword: yup
      .string()
      .required('La contraseña es requerida')
      .min(6, 'La contraseña debe tener al menos 6 caracteres'),
    confirmPassword: yup
      .string()
      .required('Confirma tu contraseña')
      .oneOf([yup.ref('newPassword')], 'Las contraseñas no coinciden'),
  })

  // Form setup with vee-validate
  const { handleSubmit, defineField, errors } = useForm({
    validationSchema: schema,
  })

  const [newPassword] = defineField('newPassword')
  const [confirmPassword] = defineField('confirmPassword')

  // API mutation
  const resetPasswordMutation = useMutation({
    mutationFn: async (data: ResetPasswordRequest) => {
      const response = await apiClient.post<ApiResponse<null>>(
        '/auth/reset-password',
        data
      )
      return response.data
    },
    onSuccess: () => {
      // Redirect to login after successful password reset
      router.push('/login')
    },
  })

  const { mutate: resetPassword, isPending, error, isSuccess } = resetPasswordMutation

  // Form submission handler
  const onSubmit = handleSubmit((values) => {
    const tokenValue = typeof token === 'string' ? token : token.value
    resetPassword({
      token: tokenValue,
      newPassword: values.newPassword,
    })
  })

  return {
    newPassword,
    confirmPassword,
    errors,
    onSubmit,
    isPending,
    error,
    isSuccess,
  }
}
