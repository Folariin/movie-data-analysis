# Configures a freshly installed SQL Server Express instance (SQLEXPRESS) for this project:
#   - enables TCP/IP on static port 1433
#   - enables SQL Server authentication ("mixed mode")
#   - restarts the service
#
# Run from an elevated (Administrator) PowerShell:
#   powershell -ExecutionPolicy Bypass -File setup\configure-sqlexpress.ps1

$ErrorActionPreference = 'Stop'

$principal = New-Object Security.Principal.WindowsPrincipal([Security.Principal.WindowsIdentity]::GetCurrent())
if (-not $principal.IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)) {
    throw 'Run this script from an Administrator PowerShell.'
}

$names = Get-ItemProperty 'HKLM:\SOFTWARE\Microsoft\Microsoft SQL Server\Instance Names\SQL' -ErrorAction SilentlyContinue
if (-not ($names -and $names.SQLEXPRESS)) {
    throw 'No SQLEXPRESS instance found. Install SQL Server Express first (see readme).'
}

$root = "HKLM:\SOFTWARE\Microsoft\Microsoft SQL Server\$($names.SQLEXPRESS)\MSSQLServer"
Set-ItemProperty -Path $root -Name LoginMode -Value 2
Set-ItemProperty -Path "$root\SuperSocketNetLib\Tcp" -Name Enabled -Value 1
Set-ItemProperty -Path "$root\SuperSocketNetLib\Tcp\IPAll" -Name TcpDynamicPorts -Value ''
Set-ItemProperty -Path "$root\SuperSocketNetLib\Tcp\IPAll" -Name TcpPort -Value '1433'

Restart-Service -Name 'MSSQL$SQLEXPRESS' -Force
Write-Output 'SQLEXPRESS now listens on TCP 1433 with SQL Server authentication enabled.'
