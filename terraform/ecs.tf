terraform {
  required_version = ">= 1.5.0"

  backend "s3" {
    bucket         = "tirula-terraform-state"
    key            = "ecs/java-langchain4j-terraform.tfstate"
    region         = "us-east-1"
    encrypt        = true
  }
}

provider "aws" {
  region = var.aws_region
}

resource "aws_ecs_cluster" "main" {
  name = "java-${var.projeto}"
}

resource "aws_cloudwatch_log_group" "java_langchain4j_logs" {
  name              = "/ecs/java-${var.projeto}"
  retention_in_days = 7
}

resource "aws_ecs_service" "app" {
  name            = "${var.projeto}-service"
  cluster         = aws_ecs_cluster.main.id
  task_definition = aws_ecs_task_definition.app.arn
  desired_count   = 1
  capacity_provider_strategy {
    capacity_provider = "FARGATE_SPOT"
    weight            = 1
  }

  network_configuration {
    subnets          = var.subnets
    assign_public_ip = true
    security_groups  = var.security_group_id
  }

  depends_on = [aws_ecs_task_definition.app]
}

resource "aws_ecs_task_definition" "app" {
  family                   = "langchain4j-taskdefinition"
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  cpu                      = "256"
  memory                   = "512"
  execution_role_arn       = var.execution_role_arn
  task_role_arn            = var.task_role_arn

  container_definitions = jsonencode([
    {
      name      = "app"
      image     = var.image_uri
      essential = true
      portMappings = [
        {
          containerPort = 8080
          hostPort      = 8080
          protocol      = "tcp"
        }
      ]
      environment = [
        {
          name  = "OPEN_API_KEY"
          value = var.open_api_key
        },
        {
          name  = "RAG_PATH"
          value = ""
        }
      ]
      logConfiguration = {
        logDriver = "awslogs"
        options = {
          awslogs-group         = aws_cloudwatch_log_group.java_langchain4j_logs.name
          awslogs-region        = var.aws_region
          awslogs-stream-prefix = "ecs"
        }
      }
    }
  ])
}
