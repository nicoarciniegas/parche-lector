# Script para configurar JAVA_HOME permanentemente (requiere Administrador)
# Ejecutar: Botón derecho PowerShell → Ejecutar como administrador → copiar y pegar esto

Write-Host "=== Configurando JAVA_HOME permanentemente ===" -ForegroundColor Cyan

$jdkPath = "C:\Program Files\Java\jdk-17"

# Verificar que existe
if (-not (Test-Path "$jdkPath\bin\java.exe")) {
    Write-Host "❌ JDK no encontrado en $jdkPath" -ForegroundColor Red
    exit 1
}

Write-Host "✅ JDK encontrado en: $jdkPath" -ForegroundColor Green

# Configurar JAVA_HOME en el sistema (Machine)
[Environment]::SetEnvironmentVariable('JAVA_HOME', $jdkPath, 'Machine')
Write-Host "✅ JAVA_HOME configurado en el sistema." -ForegroundColor Green

# Añadir bin al PATH del sistema si no está
$machinePath = [Environment]::GetEnvironmentVariable('Path', 'Machine')
if ($machinePath -notlike "*$jdkPath\bin*") {
    $newPath = "$jdkPath\bin;$machinePath"
    [Environment]::SetEnvironmentVariable('Path', $newPath, 'Machine')
    Write-Host "✅ Bin de Java añadido al PATH del sistema." -ForegroundColor Green
} else {
    Write-Host "ℹ️  Bin de Java ya estaba en PATH." -ForegroundColor Blue
}

Write-Host "`n✅ Configuración completada." -ForegroundColor Green
Write-Host "ℹ️  Cierra todas las ventanas de PowerShell y abre una nueva para aplicar los cambios." -ForegroundColor Blue
