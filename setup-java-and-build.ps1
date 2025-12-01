# Script para detectar JDK 17, configurar JAVA_HOME y compilar el backend
# Ejecutar como Administrador para cambios permanentes

Write-Host "=== Detectando JDK 17 instalado ===" -ForegroundColor Cyan

# Rutas comunes donde se instala JDK en Windows
$possiblePaths = @(
    "C:\Program Files\Eclipse Adoptium\jdk-17*",
    "C:\Program Files\Temurin\jdk-17*",
    "C:\Program Files\Java\jdk-17*",
    "C:\Program Files (x86)\Java\jdk-17*",
    "C:\openjdk-17*",
    "${env:USERPROFILE}\scoop\apps\openjdk17*\current",
    "${env:USERPROFILE}\.jdks\openjdk-17*"
)

$jdkPath = $null
foreach ($pattern in $possiblePaths) {
    $found = Get-Item $pattern -ErrorAction SilentlyContinue | Select-Object -First 1
    if ($found) {
        $jdkPath = $found.FullName
        break
    }
}

if (-not $jdkPath) {
    Write-Host "❌ No se encontró JDK 17 instalado." -ForegroundColor Red
    Write-Host "Por favor descarga e instala JDK 17 desde: https://adoptium.net" -ForegroundColor Yellow
    Write-Host "Después ejecuta este script de nuevo." -ForegroundColor Yellow
    exit 1
}

Write-Host "✅ JDK encontrado en: $jdkPath" -ForegroundColor Green

# Comprobar que exista java.exe
$javaExe = "$jdkPath\bin\java.exe"
if (-not (Test-Path $javaExe)) {
    Write-Host "❌ No se encontró java.exe en $jdkPath\bin" -ForegroundColor Red
    exit 1
}

# Configurar JAVA_HOME temporalmente para esta sesión
$env:JAVA_HOME = $jdkPath
$env:Path = "$jdkPath\bin;$env:Path"

Write-Host "`n=== Configurando JAVA_HOME ===" -ForegroundColor Cyan
Write-Host "JAVA_HOME = $env:JAVA_HOME"

# Verificar versión de Java
Write-Host "`n=== Verificando versión de Java ===" -ForegroundColor Cyan
& java -version

# Intentar configurar permanentemente (si se ejecuta con permisos)
try {
    if ([Security.Principal.WindowsIdentity]::GetCurrent().Owner.IsWellKnown([Security.Principal.WellKnownSidType]::BuiltinAdministratorsSid)) {
        Write-Host "`n=== Configurando JAVA_HOME permanentemente ===" -ForegroundColor Cyan
        [Environment]::SetEnvironmentVariable('JAVA_HOME', $jdkPath, 'Machine')
        Write-Host "✅ JAVA_HOME configurado en el sistema." -ForegroundColor Green
        
        # Añadir bin al PATH si no está
        $machinePath = [Environment]::GetEnvironmentVariable('Path','Machine')
        if ($machinePath -notlike "*$jdkPath\bin*") {
            [Environment]::SetEnvironmentVariable('Path', "$jdkPath\bin;$machinePath", 'Machine')
            Write-Host "✅ Bin de Java añadido al PATH del sistema." -ForegroundColor Green
        }
    }
} catch {
    Write-Host "⚠️  No se pudo configurar permanentemente (requiere permisos de Administrador)." -ForegroundColor Yellow
    Write-Host "    JAVA_HOME está configurado para esta sesión. Si cierras PowerShell, necesitarás ejecutar el script de nuevo." -ForegroundColor Yellow
}

# Cambiar a directorio backend
Write-Host "`n=== Compilando backend ===" -ForegroundColor Cyan
Set-Location "C:\Users\julia\Documents\GitHub\Parche-Lector\backend"

# Ejecutar Maven clean package
Write-Host "Ejecutando: .\mvnw.cmd clean package" -ForegroundColor Yellow
& .\mvnw.cmd clean package

if ($LASTEXITCODE -eq 0) {
    Write-Host "`n✅ Compilación exitosa!" -ForegroundColor Green
    
    # Buscar el JAR generado
    $jarFile = Get-ChildItem "target\*.jar" -Exclude "*-sources.jar" | Select-Object -First 1
    if ($jarFile) {
        Write-Host "`n=== Ejecutando backend ===" -ForegroundColor Cyan
        Write-Host "Iniciando: $($jarFile.Name)" -ForegroundColor Yellow
        & java -jar $jarFile.FullName
    }
} else {
    Write-Host "`n❌ Error durante la compilación. Revisa los logs arriba." -ForegroundColor Red
    exit 1
}
