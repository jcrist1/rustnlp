package dev.gigapixel.rustnlp;
import com.sun.jna.Callback
import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.Structure


internal interface TokLib: Library {
    fun Tok_destroy(handle: Pointer)
    fun Tok_create(): Pointer
    fun Tok_tokenize(handle: Pointer, text: Slice): Pointer
    fun Tok_tokenize_as_offsets(handle: Pointer, text: Slice): Pointer
}

class Tok internal constructor (
    internal val handle: Pointer,
    // These ensure that anything that is borrowed is kept alive and not cleaned
    // up by the garbage collector.
    internal val selfEdges: List<Any>
)  {

    internal class TokCleaner(val handle: Pointer, val lib: TokLib) : Runnable {
        override fun run() {
            lib.Tok_destroy(handle)
        }
    }

    companion object {
        internal val libClass: Class<TokLib> = TokLib::class.java
        internal val lib: TokLib = Native.load("rustnlp", libClass)
        
        fun create(): Tok {
            
            val returnVal = lib.Tok_create();
            val selfEdges: List<Any> = listOf()
            val handle = returnVal 
            val returnOpaque = Tok(handle, selfEdges)
            CLEANER.register(returnOpaque, Tok.TokCleaner(handle, Tok.lib));
            return returnOpaque
        }
    }
    
    fun tokenize(text: String): TokRes {
        val (textMem, textSlice) = PrimitiveArrayTools.readUtf8(text)
        
        val returnVal = lib.Tok_tokenize(handle, textSlice);
        val selfEdges: List<Any> = listOf()
        val handle = returnVal 
        val returnOpaque = TokRes(handle, selfEdges)
        CLEANER.register(returnOpaque, TokRes.TokResCleaner(handle, TokRes.lib));
        textMem.close()
        return returnOpaque
    }
    
    fun tokenizeAsOffsets(text: String): EncodeResult {
        val (textMem, textSlice) = PrimitiveArrayTools.readUtf8(text)
        
        val returnVal = lib.Tok_tokenize_as_offsets(handle, textSlice);
        val selfEdges: List<Any> = listOf()
        val handle = returnVal 
        val returnOpaque = EncodeResult(handle, selfEdges)
        CLEANER.register(returnOpaque, EncodeResult.EncodeResultCleaner(handle, EncodeResult.lib));
        textMem.close()
        return returnOpaque
    }

}
