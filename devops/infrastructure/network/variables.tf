variable "DOPPLER_OCI_TOKEN" {
  sensitive = true
  ephemeral = true
  type = string
  description = "Doppler service token with read access to OCI secrets"
  nullable = false
}

variable "APP_NAME" {
  sensitive = false
  ephemeral = false
  type = string
  description = "Application name used to prefix everything"
  nullable = false
}

variable "SUBNET_CIDR" {
  sensitive = false
  ephemeral = false
  type = string
  description = "Subnet CIDR to use for networking"
  nullable = true
  default = "10.0.0.0/24"
}