variable "OCI_TENANCY_OCID" {
  sensitive = true
  ephemeral = true
  type = string
  nullable = false
}

variable "OCI_USER_OCID" {
  sensitive = true
  ephemeral = true
  type = string
  nullable = false
}

variable "OCI_PRIVATE_KEY_PATH" {
  sensitive = true
  ephemeral = true
  type = string
  nullable = false
}

variable "OCI_FINGERPRINT" {
  sensitive = true
  ephemeral = true
  type = string
  nullable = false
}

variable "OCI_REGION" {
  sensitive = true
  ephemeral = true
  type = string
  nullable = false
}

variable "DOPPLER_TOKEN" {
  sensitive = true
  ephemeral = true
  type = string
  nullable = false
}
