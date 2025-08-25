locals {
  full_app_name = var.APP_ENVIRONMENT == "production" ? var.APP_NAME :"${var.APP_NAME}-${var.APP_ENVIRONMENT}"
}

resource "doppler_project" "app" {
  name = var.APP_NAME
}

resource "doppler_environment" "app" {
  project = doppler_project.app.name
  name = var.APP_ENVIRONMENT
  slug = var.APP_ENVIRONMENT
}

resource "doppler_secret" "app_name" {
  project = doppler_project.app.id
  config = doppler_environment.app.slug
  name = "APP_NAME"
  value = var.APP_NAME
}

resource "doppler_secret" "full_app_name" {
  project = doppler_project.app.id
  config = doppler_environment.app.slug
  name = "FULL_APP_NAME"
  value = local.full_app_name
}

resource "doppler_secret" "db_connection" {
  project = doppler_project.app.id
  config = doppler_environment.app.slug
  name = "DB_CONNECTION"
  value = "mysql"
}

resource "doppler_secret" "db_host" {
  project = doppler_project.app.id
  config = doppler_environment.app.slug
  name = "DB_HOST"
  value = "mysql"
}

resource "doppler_secret" "db_traefik_entrypoint" {
  project = doppler_project.app.id
  config = doppler_environment.app.slug
  name = "DB_TRAEFIK_ENTRYPOINT"
  value = var.DB_TRAEFIK_ENTRYPOINT
}

resource "doppler_secret" "db_port" {
  project = doppler_project.app.id
  config = doppler_environment.app.slug
  name = "DB_PORT"
  value = "3306"
}

resource "doppler_secret" "db_database" {
  project = doppler_project.app.id
  config = doppler_environment.app.slug
  name = "DB_DATABASE"
  value = var.APP_NAME
}

resource "random_password" "db_username" {
  length = 32
  special = false
}

resource "doppler_secret" "db_username" {
  project = doppler_project.app.id
  config = doppler_environment.app.slug
  name = "DB_USERNAME"
  value = random_password.db_username.result
}

resource "random_password" "db_root_password" {
  length = 56
  special = true
  override_special = "_"
}

resource "doppler_secret" "db_root_password" {
  project = doppler_project.app.id
  config = doppler_environment.app.slug
  name = "DB_ROOT_PASSWORD"
  value = random_password.db_root_password.result
}

resource "random_password" "db_password" {
  length = 56
  special = true
  override_special = "_"
}

resource "doppler_secret" "db_password" {
  project = doppler_project.app.id
  config = doppler_environment.app.slug
  name = "DB_PASSWORD"
  value = random_password.db_password.result
}

data "doppler_secrets" "cloudns" {
  project = "cloudns"
  config = "private"
}

resource "doppler_secret" "app_url" {
  project = doppler_project.app.id
  config = doppler_environment.app.slug
  name = "APP_URL"
  value = "${local.full_app_name}.${data.doppler_secrets.cloudns.map.DEFAULT_DNS_ZONE_ADDRESS}"
}

output "app_url" {
  value = nonsensitive(doppler_secret.app_url.value)
}

resource "doppler_secret" "kafka_dashboard_url" {
  project = doppler_project.app.id
  config = doppler_environment.app.slug
  name = "KAFKA_DASHBOARD_URL"
  value = "${local.full_app_name}-kafka.${data.doppler_secrets.cloudns.map.DEFAULT_DNS_ZONE_ADDRESS}"
}

output "kafka_dashboard_url" {
  value = nonsensitive(doppler_secret.kafka_dashboard_url.value)
}

resource "doppler_secret" "database_url" {
  project = doppler_project.app.id
  config = doppler_environment.app.slug
  name = "DATABASE_URL"
  value = "${local.full_app_name}-db.${data.doppler_secrets.cloudns.map.DEFAULT_DNS_ZONE_ADDRESS}"
}

output "database_url" {
  value = nonsensitive(doppler_secret.database_url.value)
}

data "doppler_secrets" "dockerhub" {
  project = "dockerhub"
  config = "private"
}

resource "doppler_secret" "dockerhub_username" {
  project = doppler_project.app.id
  config = doppler_environment.app.slug
  name = "DOCKERHUB_USERNAME"
  value = data.doppler_secrets.dockerhub.map.DOCKERHUB_USERNAME
}
