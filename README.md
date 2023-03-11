# SETUP

## Backend
1. Open project in IDEA
2. Let it download dependencies
3. start adminer + PostgreSQL
4. Start Docker Desktop if you are on Windows
```bash
docker-compose up -d
```
1. Call it in the same directory as the docker-compose.yml
2. Run the project

## Frontend
1. Open the root folder(messenger) in a terminal
2. Make sure you have node.js installed
3. Run the following commands
```bash
npm install
npm run dev
```
4. Open the project in your browser on localhost:5173

### Info
- Docker exposes ports 5433 for PostgreSQL and 8080 for Adminer
- 5433 in order to not conflict with the local PostgreSQL if you have it installed