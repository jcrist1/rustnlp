import { Console } from "console";
import cfg from "../diplomat.config.mjs";
import { readString8 } from "./diplomat-runtime.mjs";
import * as rustnlp from "./rustnlp_bg.js";

let wasm;

const imports = {
  "./rustnlp_bg.js": rustnlp,
  // // __wbg_crypto_1d1f22824a6a080c: () => {},
  // // __wbindgen_is_object: () => {},
  // // __wbg_process_4a72847cc503995b: () => {},
  // // __wbg_versions_f686565e586dd935: () => {},
  // // __wbg_node_104a2ff8d6ea03a2: () => {},
  // // __wbindgen_is_string: (o) => typeof o === string,
  // // __wbindgen_object_drop_ref: () => {},
  // __wbindgen_externref_xform__: {
  //   __wbindgen_externref_table_grow: () => {}, // }(func $_ZN12wasm_bindgen9externref31__wbindgen_externref_table_grow17hc7504e06ccca6494E (type 4)))
  //   __wbindgen_externref_table_set_null: () => {},
  // }, // (func $_ZN12wasm_bindgen9externref35__wbindgen_externref_table_set_null17hbe43454a1dbf3648E (type 0)))

  // __wbindgen_externref_xform__: () => {},
  env: {
    diplomat_console_debug_js(ptr, len) {
      console.debug(readString8(wasm, ptr, len));
    },
    diplomat_console_error_js(ptr, len) {
      console.error(readString8(wasm, ptr, len));
    },
    diplomat_console_info_js(ptr, len) {
      console.info(readString8(wasm, ptr, len));
    },
    diplomat_console_log_js(ptr, len) {
      console.log(readString8(wasm, ptr, len));
    },
    diplomat_console_warn_js(ptr, len) {
      console.warn(readString8(wasm, ptr, len));
    },
    diplomat_throw_error_js(ptr, len) {
      throw new Error(readString8(wasm, ptr, len));
    },
  },
};

if (globalThis.process?.getBuiltinModule) {
  // Node (>=22)
  const fs = globalThis.process.getBuiltinModule("fs");
  const wasmFile = new Uint8Array(fs.readFileSync(cfg["wasm_path"]));
  const loadedWasm = await WebAssembly.instantiate(wasmFile, imports);
  wasm = loadedWasm.instance.exports;
} else if (globalThis.process) {
  // Node (<22)
  const fs = await import("fs");
  console.log("Loading wasm");
  const wasmFile = new Uint8Array(fs.readFileSync(cfg["wasm_path"]));
  const loadedWasm = await WebAssembly.instantiate(wasmFile, imports);
  wasm = loadedWasm.instance.exports;
} else {
  // Browser
  const loadedWasm = await WebAssembly.instantiateStreaming(
    fetch(cfg["wasm_path"]),
    imports,
  );
  wasm = loadedWasm.instance.exports;
}

wasm.diplomat_init();
if (cfg["init"] !== undefined) {
  cfg["init"](wasm);
}

export default wasm;
