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
  doppler_token = var.DOPPLER_OCI_TOKEN
}

data "doppler_secrets" "oci" {}

provider "oci" {
  tenancy_ocid = data.doppler_secrets.oci.map.OCI_TENANCY
  user_ocid = data.doppler_secrets.oci.map.OCI_USER
  private_key = data.doppler_secrets.oci.map.OCI_PRIVATE_KEY
  fingerprint = data.doppler_secrets.oci.map.OCI_FINGERPRINT
  region = data.doppler_secrets.oci.map.OCI_REGION
}

resource "oci_identity_compartment" "app" {
  name = var.APP_NAME
  description = "${var.APP_NAME} application"
}

resource "oci_core_vcn" "app" {
  compartment_id = oci_identity_compartment.app.id
  display_name   = var.APP_NAME
  cidr_block = var.SUBNET_CIDR
}

resource "oci_core_internet_gateway" "app" {
  compartment_id = oci_identity_compartment.app.id
  vcn_id         = oci_core_vcn.app.id
  display_name   = var.APP_NAME
}

resource "oci_core_default_route_table" "app" {
  manage_default_resource_id = oci_core_vcn.app.default_route_table_id
  compartment_id = oci_identity_compartment.app.id
  display_name   = var.APP_NAME

  route_rules {
    destination       = local.firewall_public_cidr
    network_entity_id = oci_core_internet_gateway.app.id
  }
}

locals {
  firewall_tcp_protocol = "6"
  firewall_udp_protocal = "17"
  firewall_public_cidr = "0.0.0.0/0"
}

locals {
  firewall_ingress_tcp = [
    { port: 22, description: "SSH", cidr: local.firewall_public_cidr },
    { port: 3306, description: "DATABASE", cidr: local.firewall_public_cidr },
  ]

  firewall_egress_tcp = [
    { port: 53, description: "DNS", cidr: local.firewall_public_cidr },
  ]

  firewall_egress_udp = [
    { port: 53, description: "DNS", cidr: local.firewall_public_cidr },
  ]

  firewall_bidirectional_tcp = [
    { port: 80, description: "HTTP", cidr: local.firewall_public_cidr },
    { port: 443, description: "HTTPS", cidr: local.firewall_public_cidr },
    { port: 2377, description: "DOCKER SWARM", cidr: var.SUBNET_CIDR },
    { port: 7946, description: "DOKCER SWARM", cidr: var.SUBNET_CIDR },
  ]

  firewall_bidirectional_udp = [
    { port: 4789, description: "DOCKER SWARM", cidr: var.SUBNET_CIDR },
    { port: 7946, description: "DOCKER SWARM", cidr: var.SUBNET_CIDR },
    { port: 7946, description: "DOKCER SWARM", cidr: var.SUBNET_CIDR },
  ]
}

resource "oci_core_default_security_list" "app" {
  manage_default_resource_id = oci_core_vcn.app.default_security_list_id
  compartment_id = oci_identity_compartment.app.id
  display_name   = var.APP_NAME

  dynamic "ingress_security_rules" {
    for_each = local.firewall_ingress_tcp
    content {
      protocol = local.firewall_tcp_protocol
      source = ingress_security_rules.value.cidr
      description = ingress_security_rules.value.description
      tcp_options {
        min = ingress_security_rules.value.port
        max = ingress_security_rules.value.port
      }
    }
  }

  dynamic "egress_security_rules" {
    for_each = local.firewall_egress_tcp
    content {
      protocol = local.firewall_tcp_protocol
      destination = egress_security_rules.value.cidr
      description = egress_security_rules.value.description
      tcp_options {
        min = egress_security_rules.value.port
        max = egress_security_rules.value.port
      }
    }
  }

  dynamic "egress_security_rules" {
    for_each = local.firewall_egress_udp
    content {
      protocol = local.firewall_udp_protocal
      destination = egress_security_rules.value.cidr
      description = egress_security_rules.value.description
      udp_options {
        min = egress_security_rules.value.port
        max = egress_security_rules.value.port
      }
    }
  }

  dynamic "ingress_security_rules" {
    for_each = local.firewall_bidirectional_tcp
    content {
      protocol = local.firewall_tcp_protocol
      source = ingress_security_rules.value.cidr
      description = ingress_security_rules.value.description
      tcp_options {
        min = ingress_security_rules.value.port
        max = ingress_security_rules.value.port
      }
    }
  }

  dynamic "egress_security_rules" {
    for_each = local.firewall_bidirectional_tcp
    content {
      protocol = local.firewall_tcp_protocol
      destination = egress_security_rules.value.cidr
      description = egress_security_rules.value.description
      tcp_options {
        min = egress_security_rules.value.port
        max = egress_security_rules.value.port
      }
    }
  }

  dynamic "ingress_security_rules" {
    for_each = local.firewall_bidirectional_udp
    content {
      protocol = local.firewall_udp_protocal
      source = ingress_security_rules.value.cidr
      description = ingress_security_rules.value.description
      udp_options {
        min = ingress_security_rules.value.port
        max = ingress_security_rules.value.port
      }
    }
  }

  dynamic "egress_security_rules" {
    for_each = local.firewall_bidirectional_udp
    content {
      protocol = local.firewall_udp_protocal
      destination = egress_security_rules.value.cidr
      description = egress_security_rules.value.description
      udp_options {
        min = egress_security_rules.value.port
        max = egress_security_rules.value.port
      }
    }
  }
}

resource "oci_core_subnet" "my_subnet" {
  cidr_block        = var.SUBNET_CIDR
  compartment_id    = oci_identity_compartment.app.id
  vcn_id            = oci_core_vcn.app.id
  display_name      = "${var.APP_NAME}-subnet"
  security_list_ids = [oci_core_default_security_list.app.id]
  route_table_id    = oci_core_default_route_table.app.id
}


# data "oci_identity_availability_domains" "domains" {
#   compartment_id = oci_identity_compartment.app.id
# }

# output "name" {
#   value = nonsensitive(data.oci_identity_availability_domains.domains.availability_domains[0].name)
#   sensitive = false
# }