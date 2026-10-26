# VEX NFT Collection

A Full Stack Web3 project that combines an **ERC-721 NFT smart contract deployed on Arbitrum One** with a **Java Spring Boot REST API** capable of reading blockchain data and retrieving decentralized NFT metadata from IPFS.

The project demonstrates the integration between traditional backend development and Web3 technologies using **Solidity, Foundry, OpenZeppelin, Java, Spring Boot and Web3j**.

---

## Overview

VEX NFT Collection started as an ERC-721 smart contract developed with Foundry and OpenZeppelin.

The project was later extended with a traditional Java backend to demonstrate how a Web2 application can interact with a blockchain smart contract.

The Spring Boot backend connects to **Arbitrum One through JSON-RPC using Web3j**, reads information directly from the deployed smart contract and retrieves NFT metadata stored on **IPFS**.

The backend currently allows users to:

- Check the connection with Arbitrum One.
- Read collection information directly from the smart contract.
- Retrieve NFT ownership information.
- Retrieve the `tokenURI` of an NFT.
- Fetch decentralized NFT metadata from IPFS.
- Handle nonexistent NFTs with proper HTTP responses.
- Explore and test the API through Swagger/OpenAPI.

---

## Architecture

```text
                    Client
                      │
                      │ HTTP
                      ▼
              ┌─────────────────┐
              │   Spring Boot   │
              │    REST API     │
              └────────┬────────┘
                       │
                       │ Web3j / JSON-RPC
                       ▼
              ┌─────────────────┐
              │  Arbitrum One   │
              │                 │
              │ VEXCollection   │
              │    ERC-721      │
              └────────┬────────┘
                       │
                       │ tokenURI
                       ▼
              ┌─────────────────┐
              │      IPFS       │
              │  NFT Metadata   │
              └─────────────────┘
```

The blockchain remains the source of truth for NFT ownership, while IPFS stores the decentralized metadata and images associated with each token.

---

## Tech Stack

### Smart Contracts

- Solidity
- Foundry
- OpenZeppelin Contracts
- ERC-721

### Blockchain

- Arbitrum One
- Ethereum JSON-RPC
- IPFS
- Pinata Gateway

### Backend

- Java 21
- Spring Boot
- Spring Web
- Web3j
- Maven
- Swagger / OpenAPI

---

## Smart Contract

The VEX NFT Collection smart contract is deployed on **Arbitrum One**.

**Network**

```text
Arbitrum One
```

**Chain ID**

```text
42161
```

**Contract Address**

```text
0xcacde617dcd48d7624b845a218600be401c77d7d
```

The contract implements the ERC-721 NFT standard using OpenZeppelin.

Some of the functions consumed by the backend include:

```solidity
name()
symbol()
ownerOf(uint256 tokenId)
tokenURI(uint256 tokenId)
```

---

## NFT Metadata

NFT metadata is stored on IPFS.

Example token URI:

```text
ipfs://bafybeiahtxroxsf7j53qnanzgav7x3sjkzrj5brydw4zjxxgamamyqh2cm/0.json
```

Example metadata:

```json
{
  "name": "VEX NFT 0",
  "description": "First NFT of the VEX collection",
  "image": "ipfs://bafybeihshhygwrnnzvaigv43bpd2esgydbtspieuup7zyxrq7myigidhy4"
}
```

The backend obtains the `tokenURI` from the smart contract and retrieves the corresponding metadata through an IPFS gateway.

---

## REST API

The backend exposes the following endpoints.

### Blockchain Status

```http
GET /api/blockchain/status
```

Checks the connection with Arbitrum One.

Example response:

```json
{
  "network": "Arbitrum One",
  "latestBlock": 518253277,
  "connected": true,
  "chainId": 42161
}
```

---

### Collection Information

```http
GET /api/collection
```

Reads the collection name and symbol directly from the deployed smart contract.

Example response:

```json
{
  "network": "Arbitrum One",
  "name": "VEX Collection",
  "symbol": "VEX"
}
```

---

### NFT Information

```http
GET /api/nfts/{tokenId}
```

Retrieves:

- Token ID
- NFT name
- Description
- IPFS image
- Current owner
- Token URI
- Network
- Contract address

Example:

```http
GET /api/nfts/0
```

Example response:

```json
{
  "tokenId": 0,
  "name": "VEX NFT 0",
  "description": "First NFT of the VEX collection",
  "image": "ipfs://bafybeihshhygwrnnzvaigv43bpd2esgydbtspieuup7zyxrq7myigidhy4",
  "owner": "0xea93b3edea17d3307276ee3093386465c933fe2c",
  "tokenUri": "ipfs://bafybeiahtxroxsf7j53qnanzgav7x3sjkzrj5brydw4zjxxgamamyqh2cm/0.json",
  "network": "Arbitrum One",
  "contractAddress": "0xcacde617dcd48d7624b845a218600be401c77d7d"
}
```

---

## Error Handling

The API includes centralized exception handling.

If a requested NFT does not exist:

```http
GET /api/nfts/999
```

the API returns:

```json
{
  "status": 404,
  "error": "NFT not found",
  "message": "Token 999 does not exist",
  "timestamp": "2026-10-26T20:30:00"
}
```

instead of exposing an internal blockchain or Java stack trace.

---

## Swagger / OpenAPI

The REST API is documented with Swagger.

After starting the backend, Swagger UI is available at:

```text
http://localhost:8080/swagger-ui/index.html
```

From Swagger UI it is possible to execute and test all available endpoints directly.

---

## Project Structure

```text
nft-collection/
│
├── src/
│   └── VEXCollection.sol
│
├── script/
│   └── DeployNFTCollection.s.sol
│
├── test/
│
├── lib/
│   ├── forge-std/
│   └── openzeppelin-contracts/
│
├── foundry.toml
│
└── backend/
    │
    ├── pom.xml
    │
    └── src/
        └── main/
            ├── java/
            │   └── com/vexnft/nftbackend/
            │       ├── config/
            │       ├── controller/
            │       ├── dto/
            │       ├── exception/
            │       └── service/
            │
            └── resources/
                └── application.yml
```

---

## Backend Architecture

The backend follows a simple layered architecture:

```text
Controller
    │
    ▼
Service
    │
    ▼
Web3j
    │
    ▼
Arbitrum One
```

### Controllers

Expose REST endpoints to API consumers.

### Services

Contain the application and blockchain interaction logic.

### DTOs

Define the API response models.

### Config

Contains Web3j and OpenAPI configuration.

### Exception Handling

Converts blockchain errors into appropriate HTTP responses.

---

## Running the Smart Contract Project

Make sure Foundry is installed.

Check the installation:

```bash
forge --version
```

Build the project:

```bash
forge build
```

Run the tests:

```bash
forge test
```

---

## Running the Backend

Navigate to the backend directory:

```bash
cd backend
```

Run the Spring Boot application:

```bash
./mvnw spring-boot:run
```

Alternatively, run the application directly from your IDE.

The backend will start on:

```text
http://localhost:8080
```

---

## Configuration

Example `application.yml`:

```yaml
spring:
  application:
    name: nft-backend

server:
  port: 8080

blockchain:
  rpc-url: "https://arb1.arbitrum.io/rpc"
  contract-address: "0xcacde617dcd48d7624b845a218600be401c77d7d"
  chain-id: 42161

ipfs:
  gateway: "https://gateway.pinata.cloud/ipfs/"
```

No private key is required by the backend because the current API only performs read operations against the blockchain.

---

## Web3 Flow

When requesting:

```http
GET /api/nfts/0
```

the backend performs the following process:

```text
1. Client requests NFT #0

2. Spring Boot receives the request

3. Web3j calls:
   ownerOf(0)

4. Arbitrum returns the current owner

5. Web3j calls:
   tokenURI(0)

6. Arbitrum returns:
   ipfs://.../0.json

7. Spring Boot converts the IPFS URI to an HTTP gateway request

8. NFT metadata is retrieved from IPFS

9. Blockchain data and metadata are combined

10. The REST API returns the complete NFT information
```

---

## What This Project Demonstrates

This project demonstrates how traditional backend technologies can be integrated with decentralized applications.

It includes:

- ERC-721 smart contract development.
- Smart contract deployment to a production blockchain.
- OpenZeppelin integration.
- Foundry development workflow.
- On-chain data retrieval.
- Java-to-blockchain communication using Web3j.
- REST API development with Spring Boot.
- Decentralized metadata storage with IPFS.
- OpenAPI documentation.
- Global REST exception handling.
- Web2 and Web3 architecture integration.

---

## Future Improvements

Possible future improvements include:

- Event indexing using ERC-721 `Transfer` events.
- PostgreSQL persistence.
- NFT ownership history.
- Wallet-based authentication.
- Frontend application.
- NFT minting through the backend.
- Dedicated IPFS gateway.
- Caching blockchain and IPFS responses.
- Deployment of the Spring Boot API.

These features are intentionally outside the current scope, as this version focuses on demonstrating a clean integration between a traditional Java backend and an ERC-721 smart contract.

---

## Author

Developed as part of my journey toward becoming a **Full Stack Web3 Developer**, combining traditional backend development with blockchain technologies.

---

## License

This project is intended for educational and portfolio purposes.
