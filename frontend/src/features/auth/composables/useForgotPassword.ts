// Composable for Forgot Password endpoint
import { useMutation } from '@tanstack/vue-query'
import { useForm } from 'vee-validate'
import * as yup from 'yup'
import { apiClient } from '../../../shared/api/apiClient'
import type { ApiResponse, ForgotPasswordRequest } from '../../../shared/types/types'

export const useForgotPassword = () => {
  // Validation schema
  const schema = yup.object({
    email: yup
      .string()
      .required('El email es requerido')
      .email('Debe ser un email válido'),
  })

  // Form setup with vee-validate
  const { handleSubmit, defineField, errors } = useForm({
    validationSchema: schema,
  })

  const [email] = defineField('email')

  // API mutation
  const forgotPasswordMutation = useMutation({
    mutationFn: async (data: ForgotPasswordRequest) => {
      const response = await apiClient.post<ApiResponse<null>>(
        '/auth/forgot-password',
        data
      )
      return response.data
    },
  })

  const { mutate: sendResetEmail, isPending, error, isSuccess } = forgotPasswordMutation

  // Form submission handler
  const onSubmit = handleSubmit((values) => {
    sendResetEmail({ email: values.email })
  })

  return {
    email,
    errors,
    onSubmit,
    isPending,
    error,
    isSuccess,
  }
}
