locals {
  proxy_stack_target_location = "${local.stacks_target_location}/${local.proxy_stack_name}/docker-compose.yml"
  proxy_address = "${var.SERVER_NAME}-proxy.${data.doppler_secrets.cloudns.map.DEFAULT_DNS_ZONE_ADDRESS}"
}

resource "doppler_environment" "proxy" {
  project = doppler_project.server.name
  name = "proxy"
  slug = "proxy"
}

resource "doppler_secret" "proxy_address" {
  project = doppler_project.server.id
  config = doppler_environment.proxy.slug
  name = "PROXY_ADDRESS"
  value = local.proxy_address
}

resource "doppler_secret" "acme_email" {
  project = doppler_project.server.id
  config = doppler_environment.proxy.slug
  name = "DNS_ACME_EMAIL"
  value = data.doppler_secrets.cloudns.map.DEFAULT_DNS_ZONE_EMAIL_ADDRESS
}

output "cloudns_proxy_record_url" {
  value = "${var.SERVER_NAME}-proxy"
}

output "cloudns_proxy_record_ip" {
  value = oci_core_instance.instance.public_ip
}