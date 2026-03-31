# TCP Server (Projeto de Faculdade)

Projeto acadêmico em Java para praticar comunicação via **sockets TCP**.

O sistema tem dois programas:

- **Servidor**: aceita múltiplos clientes com threads.
- **Cliente**: conecta ao servidor, envia mensagens e pode requisitar um arquivo.

## Objetivo do projeto

Demonstrar, de forma simples, os conceitos de:

- conexão cliente-servidor com TCP;
- concorrência no servidor (1 thread por cliente);
- leitura de mensagens de texto;
- envio de arquivo binário pelo socket;
- cálculo de hash SHA-256 do arquivo no servidor.

## Estrutura

```text
.
├── Client/
│   └── TCPClient.java
└── Server/
    ├── File.txt
    └── src/
        ├── Main.java
        ├── ClientHandler.java
        └── FileFinder.java
```

## Pré-requisitos

- Java JDK 8+ instalado (`javac` e `java` no PATH).

## Como executar

### 1) Iniciar o servidor

No terminal:

```bash
cd Server
javac src/*.java
java -cp src Main
```

O servidor sobe na porta `12345`.

### 2) Iniciar o cliente

Em outro terminal:

```bash
cd Client
javac TCPClient.java
java TCPClient
```

O cliente conecta em `127.0.0.1:12345`.

## Como usar

Depois de conectar, digite mensagens no cliente e pressione Enter.

- Para conversa simples: qualquer texto.
- Para pedir arquivo: `arquivo NomeDoArquivo.ext`
- Para encerrar: `sair`

Exemplo:

```text
arquivo File.txt
```

Quando o arquivo existe, o servidor responde com:

- status;
- nome do arquivo;
- tamanho (bytes, KB, MB);
- hash SHA-256;
- conteúdo do arquivo enviado em seguida pelo socket.

No cliente atual, o arquivo recebido é salvo como `arquivo.txt` no diretório de execução.

## Observações importantes

- O servidor procura o arquivo no **diretório em que ele foi iniciado** (normalmente `Server/`).
- A implementação de transferência encerra quando o stream fecha; para testes locais simples funciona, mas não há protocolo robusto com delimitador/tamanho para múltiplas transferências na mesma sessão.
- O código foi feito para fins didáticos de faculdade.

## Licença

Este projeto está sob a licença MIT. Veja [LICENSE](./LICENSE).
