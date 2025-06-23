terraform {
  required_providers {
    doppler = {
      source = "DopplerHQ/doppler"
      version = "1.18.0"
    }
    oci = {
      source = "oracle/oci"
      version = "7.5.0"
    }
  }
}

provider "doppler" {
  doppler_token = var.DOPPLER_TOKEN
}

data "doppler_secrets" "oci" {}

provider "oci" {
  tenancy_ocid = var.OCI_TENANCY_OCID
  user_ocid = var.OCI_USER_OCID
  private_key = var.OCI_PRIVATE_KEY_PATH
  fingerprint = var.OCI_FINGERPRINT
  region = var.OCI_REGION
}

/**
Creer user
créer policy
creer environnement doppler (ou config)
créer les secrets nécessaires
utiliser ses secrets dans nos projets
*/

