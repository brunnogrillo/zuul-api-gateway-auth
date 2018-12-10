export EUREKA_IP=$(ipconfig getifaddr en0)
docker-compose -f docker-compose.dev.yml up --build
