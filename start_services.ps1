# Define the root directory
$rootDir = "c:/Users/EliteBook 840 G7/Desktop/E_COMMERCE_APPLICATION"

# Define the services to start in order
$services = @(
    @{ Name = "config-server"; Path = "services/config-server"; Wait = 15 },
    @{ Name = "discovery"; Path = "services/discovery"; Wait = 15 },
    @{ Name = "customer"; Path = "services/customer"; Wait = 0 },
    @{ Name = "product"; Path = "services/product"; Wait = 0 }
)

Write-Host "Starting microservices..."

foreach ($service in $services) {
    $serviceName = $service.Name
    $servicePath = Join-Path $rootDir $service.Path
    $waitTime = $service.Wait

    Write-Host "----------------------------------------"
    Write-Host "🚀 Starting $serviceName in a new PowerShell window..."
    Write-Host "----------------------------------------"

    if (Test-Path $servicePath) {
        # Fixed: Wrap the entire argument list in a single string
        Start-Process powershell -WorkingDirectory $servicePath -ArgumentList "-NoExit", "-Command", "mvn clean install spring-boot:run"
        
        if ($waitTime -gt 0) {
            Write-Host "Waiting for $serviceName to initialize for $waitTime seconds..."
            Start-Sleep -Seconds $waitTime
        }
    } else {
        Write-Host "⚠️ Directory $servicePath for $serviceName does not exist, skipping..." -ForegroundColor Yellow
    }

    Write-Host ""
}

Write-Host "🎯 All services have been launched. Check individual PowerShell windows for status." -ForegroundColor Cyan