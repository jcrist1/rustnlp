# rustnlpjs

Install dependencies:

```bash
bun install
```

build wasm from parent directory

```bash
cargo build --release --target=wasm32-unknown-unknown
```

copy wasm to `./api/` in this directory

```bash
cp ../target/wasm32-unknown-unknown/relase/rustnlp.wasm api/
```

Run wasm bindgen

```bash
wasm-bindgen api/rustnlp.wasm
```

To run:

```bash
bun index.ts
```

To run the benchmark run

```bash
bun bench.ts
```
