package dev.gigapixel.rustnlp

import org.junit.jupiter.api.Assertions
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;


    @State(Scope.Benchmark)
class BenchOffsetsVsStrings {
    private val tok = Tok.create()

        val data = """
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec gravida porta ligula. Donec nec turpis sed tortor egestas posuere. Phasellus malesuada fermentum ipsum id vehicula. Nunc sapien nunc, convallis a varius vitae, tincidunt et orci. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum augue erat, vehicula eget ornare a, pharetra sit amet elit. Fusce quis varius nulla. Donec dui ligula, tincidunt sit amet tincidunt sit amet, rutrum sed urna. Maecenas imperdiet interdum dui. Aliquam nec euismod leo. Etiam non enim ex. Duis porttitor bibendum augue eu aliquet. Sed congue augue et gravida mollis. Proin lacinia, diam in finibus cursus, libero dolor posuere metus, ac convallis dolor enim at orci.

            Cras volutpat tincidunt sapien ut ullamcorper. Fusce finibus, nisl quis egestas malesuada, eros sem tincidunt lacus, eu imperdiet sem purus vitae turpis. Sed felis nunc, semper et nunc sit amet, consequat consectetur quam. Phasellus tempor eu justo a tempor. Donec neque arcu, venenatis a urna non, volutpat tincidunt felis. Aliquam ac ullamcorper massa. In nibh ligula, egestas eu placerat eu, placerat eget libero. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Vivamus magna tellus, semper ut mi sit amet, feugiat hendrerit tortor. Donec ut dignissim orci. Maecenas suscipit, nibh id sodales condimentum, risus quam aliquet elit, eget pellentesque orci orci et justo. Phasellus sit amet elit suscipit, malesuada ipsum ut, aliquam justo. Vestibulum convallis enim quam. Curabitur porta leo sit amet vehicula iaculis. Integer eu sem mi.

            Quisque sed tellus ante. Maecenas commodo tristique diam vel lobortis. Vestibulum sit amet luctus erat. Maecenas commodo justo quis tortor scelerisque, quis tempus odio imperdiet. Phasellus ullamcorper elit at libero ultrices, at mollis velit euismod. Pellentesque posuere ante bibendum nisl pulvinar, eget maximus turpis elementum. Nam facilisis venenatis est, non scelerisque massa ultrices eleifend. Quisque non lorem ut ligula maximus consequat. Suspendisse lobortis placerat orci, in varius quam bibendum a. Suspendisse eget aliquam lorem, vel pretium lacus.

            Donec fermentum lacinia sem in tempor. Morbi ac ultrices nisl. Fusce aliquet euismod nulla ut elementum. Fusce ut neque euismod, scelerisque nisl ac, luctus ligula. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nulla semper, elit eu fermentum rhoncus, magna leo congue mi, id convallis dolor leo vitae nisi. Aliquam quam mauris, laoreet nec sagittis sed, sagittis ut velit. Vestibulum tempor aliquet sodales. Curabitur sit amet lobortis eros, ut sodales eros.

            Aenean porta luctus eros, at blandit est suscipit in. Suspendisse potenti. Donec congue finibus commodo. Suspendisse potenti. Fusce consequat lorem sit amet magna rutrum, at condimentum nisi dictum. Duis consequat, urna nec tempus venenatis, risus quam pretium velit, quis tempus diam turpis a ex. Vestibulum mollis purus sed erat mollis, blandit suscipit tortor sollicitudin. 
        """.trimIndent()
//        private val locale = ICU4XLocale.new_("en")
//        private val provider = ICU4XDataProvider.newStatic()
//        private val options = ICU4XFixedDecimalFormatterOptions.default_()
//        private val formatter = ICU4XFixedDecimalFormatter.tryNew(locale, provider, options).wrapErrAndThrow()
//        private val decimal = ICU4XFixedDecimal.new_(123)
        @Benchmark
        fun benchOffsets(bh: Blackhole) {
            val encode = tok.tokenizeAsOffsets(data).wrapErrAndThrow()
            encode[0]

            bh.consume(encode)
        }
}