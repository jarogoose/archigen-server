# ARCHIGEN PROJECT #

## Environments ##

To start prod environment use:

```sh
docker compose -f docker-compose.dev.yaml -p archigen-dev up -d
```

To start dev environment use:

```sh
docker compose -p archigen up -d
```

To start mongo db server for integration tests use:

```sh
docker compose -f docker-compose.test.yaml -p archigen-it-server up -d
```

To start mongo db server for local environment use:

```sh
docker compose -f docker-compose.local.yaml -p archigen-local-server up -d
```
