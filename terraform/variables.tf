variable "aws_region" {
  type = string
}

variable "image_uri" {
  description = "Imagem Docker no ECR (ex: 123.dkr.ecr.us-east-1.amazonaws.com/app:sha)"
  type        = string
}

variable "security_group_id" {
  type = list(string)
}

variable "subnets" {
  type = list(string)
}

variable "execution_role_arn" {
  type = string
}

variable "task_role_arn" {
  type = string
}

variable "projeto" {
  type = string
  default = "langchain4j"
}

variable "open_api_key" {
  type = string
}

