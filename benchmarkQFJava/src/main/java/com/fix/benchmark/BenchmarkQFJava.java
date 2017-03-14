/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.fix.benchmark;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import quickfix.fix44.MarketDataRequest;
import quickfix.fix44.NewOrderMultileg;
import quickfix.fix44.QuoteStatusRequest;


public class BenchmarkQFJava {

    private static quickfix.DataDictionary MakeDD()
    {
        try
        {
            return new quickfix.DataDictionary("FIX44.xml");
        }
        catch (quickfix.ConfigError err)
        {
            System.out.printf("#### failed to make DataDictionary ####");
            return null;
        }
    }

    @State(Scope.Thread)
    public static class MyState {
        String quoteStatusRequestWithGrpBytes          = "8=FIX.4.4\u00019=140\u000135=a\u000134=99\u000149=senderCompID\u000152=20071123-05:30:00.000\u000156=targetCompID\u000155=ABCDE\u0001555=3\u0001600=BCDEF\u0001601=PQRS\u0001600=CDEFG\u0001601=QRST\u0001600=DEFGH\u0001601=RSTU\u000110=134\u0001";
        String quoteStatusRequestWithNestedGrpsBytes   = "8=FIX.4.4\u00019=246\u000135=a\u000134=99\u000149=senderCompID\u000152=20071123-05:30:00.000\u000156=targetCompID\u000155=ABCDE\u0001555=3\u0001600=BCDEF\u0001601=PQRS\u0001604=2\u0001605=12345\u0001606=QWERTYU\u0001605=23456\u0001606=ASDFGHJ\u0001600=CDEFG\u0001601=QRST\u0001604=1\u0001605=34567\u0001606=ZXCVBNM\u0001600=DEFGH\u0001601=RSTU\u0001604=1\u0001605=45678\u0001606=POIUYTR\u000110=043\u0001";
        String newOrderMultiLegBytes                   = "8=FIX.4.4\u00019=1491\u000135=AB\u000134=99\u000149=senderCompID\u000152=20071123-05:30:00.000\u000156=targetCompID\u000111=PZXWQ\u0001526=EFZDRSMQ\u0001453=0\u0001229=20170122\u000175=20170122\u00011=PKNOIWA\u0001660=3\u0001581=2\u0001589=0\u0001590=1\u000170=ZNOSMQBF\u000178=0\u000163=9\u000164=20170122\u0001544=2\u0001635=H\u000121=3\u0001110=792281624589241053767102627.850000000000000\u0001111=3.689348814312414\u0001100=UYJN\u0001386=0\u000181=0\u000154=F\u000155=RSWQE\u000165=WI\u000148=FJXY\u000122=D\u0001454=0\u0001461=WAUIJ\u0001167=TIPS\u0001762=JXYCWAL\u0001200=201701w1\u0001541=20170122\u0001201=0\u0001224=20170122\u0001239=0\u0001226=1\u0001227=-5534023222112865484.700000000000000\u0001228=79228162477.370849433239945\u0001255=ESTXRVJK\u0001543=YSWHLF\u0001470=IMGKYZT\u0001472=VZNO\u0001240=20170122\u0001202=79228162458924105376.710262785000000\u0001947=CWKL\u0001206=4\u0001231=-3689348814312413.593900000000000\u0001223=-792281.625142643375807\u0001106=GHBFTU\u0001348=2\u0001349=AA\u0001107=QUOJDHBP\u0001350=2\u0001351=BB\u0001691=DRSWQEF\u0001875=-2\u0001876=UVZTX\u0001864=0\u0001873=20170122\u0001874=20170122\u0001140=-7922816249581759353.271930061100000\u0001555=0\u0001114=N\u000160=20170122-06:54:00\u0001854=1\u0001152=-0.000000003689349\u0001516=-5534.023223401355674\u0001468=1\u0001469=792281.624958175935327\u000140=M\u000144=36.893488143124136\u000199=79228162477370849459009.748992000000000\u000115=USD\u0001376=NHVWAUY\u0001377=Y\u000123=ALPJNB\u0001117=KOCDXBP\u0001168=20170122-06:54:00\u0001432=20170122\u0001126=20170122-06:54:00\u0001427=2\u000112=-79228.162495817593533\u000113=4\u0001479=VZTHIMGU\u0001497=N\u0001529=7\u0001582=4\u0001121=Y\u0001120=ZTXLMG\u0001775=0\u000158=MGBVZT\u0001354=2\u0001355=CC\u000177=C\u0001203=0\u0001210=-792281.624773708494590\u0001211=-79228162477370849.454714781696000\u0001836=1\u0001837=2\u0001838=2\u0001840=1\u0001388=6\u0001389=-79228162514264337580659048.449000000000000\u0001841=1\u0001842=0\u0001843=0\u0001844=1\u0001846=1\u0001847=-3\u0001848=UOSMHBFZ\u0001849=-55.340232221128655\u0001480=N\u0001481=1\u0001513=EFJDHS\u0001563=1\u000110=000\u0001";
        String marketDataRequestBytes                  = "8=FIX.4.4\u00019=138\u000135=V\u000134=99\u000149=senderCompID\u000152=20071123-05:30:00.000\u000156=targetCompID\u0001262=MDRQ-1487838687427\u0001263=1\u0001264=1\u0001267=2\u0001269=0\u0001269=1\u0001146=1\u000155=EUR/USD\u000110=133\u0001";
        quickfix.DataDictionary dataDictionary        = MakeDD();
        QuoteStatusRequest qsr = MessageFactory.MakeQuoteStatusRequestIncGroup();
        QuoteStatusRequest qsrng = MessageFactory.MakeQuoteStatusRequestIncNestedGroup();
        MarketDataRequest mdr = MessageFactory.MakeMarketDataRequest();
        NewOrderMultileg noml = MessageFactory.MakeNewOrderMultileg();
    }


    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public String WriteMarketDataRequest(MyState state)
    {
        return state.mdr.toString();
    }


    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public String WriteQuoteStatusRequestMsgWithGroup(MyState state)
    {
        return state.qsr.toString();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public String WriteQuoteStatusRequestWithNestedGroup(MyState state)
    {
        return state.qsrng.toString(); // ToString is how QuickFixN messages write themselves
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public String WriteNewOrderMultiLeg(MyState state)
    {
        return state.noml.toString();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public quickfix.Message ReadMarketDataRequest(MyState state) throws quickfix.InvalidMessage
    {
        quickfix.Message msg = new quickfix.Message();
        //msg.fromString(state.marketDataRequestBytes, state.dataDictionary, true);
        msg.fromString(state.marketDataRequestBytes, state.dataDictionary, state.dataDictionary,true);
        return msg;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public quickfix.Message ReadQuoteStatusRequestWithGrps(MyState state) throws quickfix.InvalidMessage
    {
        quickfix.Message msg = new quickfix.Message();
        //msg.fromString(state.quoteStatusRequestWithGrpBytes, state.dataDictionary, true);
        msg.fromString(state.quoteStatusRequestWithGrpBytes, state.dataDictionary, state.dataDictionary,true);
        return msg;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public quickfix.Message ReadQuoteStatusRequestWithNestedGrps(MyState state) throws quickfix.InvalidMessage
    {
        quickfix.Message msg = new quickfix.Message();
        //msg.fromString(state.quoteStatusRequestWithNestedGrpsBytes, state.dataDictionary, true);
        msg.fromString(state.quoteStatusRequestWithNestedGrpsBytes, state.dataDictionary, state.dataDictionary,true);
        return msg;
    }


    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public quickfix.Message ReadNewOrderMultileg(MyState state) throws quickfix.InvalidMessage
    {
        quickfix.Message msg = new quickfix.Message();
        //msg.fromString(state.newOrderMultilegBytes, state.dataDictionary, true);
        msg.fromString(state.newOrderMultiLegBytes, state.dataDictionary, state.dataDictionary,true);
        return msg;
    }


    public static void main(String[] args)
    {
        try
        {
            Options opt = new OptionsBuilder()
                    .include(BenchmarkQFJava.class.getSimpleName())
                    .forks(1)
                    .build();

            new Runner(opt).run();
        }
        catch( Exception ex)
        {
            System.out.printf("error: %s", ex.getMessage());
        }
    }
}
