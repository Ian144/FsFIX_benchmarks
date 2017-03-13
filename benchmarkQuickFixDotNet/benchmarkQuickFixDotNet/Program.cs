using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using QuickFix.FIX44;
using QuickFix.Fields;

using BenchmarkDotNet.Attributes;
using BenchmarkDotNet.Running;
using BenchmarkDotNet.Configs;
using BenchmarkDotNet.Diagnosers;
using BenchmarkDotNet.Jobs;
using BenchmarkDotNet.Columns;
using BenchmarkDotNet.Attributes.Columns;
using BenchmarkDotNet.Diagnostics.Windows.Configs;

namespace BenchmarkRW
{

    //[MemoryDiagnoser, InliningDiagnoser]
    [MemoryDiagnoser, BenchmarkDotNet.Attributes.Columns.MedianColumn]
    //[MinColumn, MaxColumn, BenchmarkDotNet.Attributes.Columns.MedianColumn, BenchmarkDotNet.Attributes.Columns.AllStatisticsColumn]
    public class QFBenchmark
    {
        private MarketDataRequest mdr = MessageFactory.MakeMarketDataRequest();
        private NewOrderMultileg noml = MessageFactory.MakeNewOrderMultileg();
        private QuoteStatusRequest qsrG = MessageFactory.MakeQuoteStatusRequestIncGroup();
        private QuoteStatusRequest qsrNG = MessageFactory.MakeQuoteStatusRequestIncNestedGroup();


        //private string mdrStr = "8=FIX.4.4\u00019=75\u000135=V\u0001262=MDRQ-1488455204910\u0001263=1\u0001264=1\u0001267=2\u0001269=0\u0001269=1\u0001146=1\u000155=EUR/USD\u000110=216\u0001";
        //                      "8=FIX.4.4     |9=138|    35=V|     262=MDRQ-1487838687427|     263=1|     264=1|     267=2|     269=0|     269=1|     146=1|     55=EUR/USD      10=133||               34=99|49=senderCompID|52=20071123-05:30:00.000|56=targetCompID||"B
        private QuickFix.FIX44.MessageFactory msgFactory = new QuickFix.FIX44.MessageFactory();
        private QuickFix.DataDictionary.DataDictionary dataDictionary = new QuickFix.DataDictionary.DataDictionary();

        private string marketDataRequestBytes = "8=FIX.4.4\u00019=138\u000135=V\u000134=99\u000149=senderCompID\u000152=20071123-05:30:00.000\u000156=targetCompID\u0001262=MDRQ-1487838687427\u0001263=1\u0001264=1\u0001267=2\u0001269=0\u0001269=1\u0001146=1\u000155=EUR/USD\u000110=133\u0001";
        private string quoteStatusRequestWithGrpBytes = "8=FIX.4.4\u00019=140\u000135=a\u000134=99\u000149=senderCompID\u000152=20071123-05:30:00.000\u000156=targetCompID\u000155=ABCDE\u0001555=3\u0001600=BCDEF\u0001601=PQRS\u0001600=CDEFG\u0001601=QRST\u0001600=DEFGH\u0001601=RSTU\u000110=134\u0001";
        private string quoteStatusRequestWithNestedGrpsBytes = "8=FIX.4.4\u00019=246\u000135=a\u000134=99\u000149=senderCompID\u000152=20071123-05:30:00.000\u000156=targetCompID\u000155=ABCDE\u0001555=3\u0001600=BCDEF\u0001601=PQRS\u0001604=2\u0001605=12345\u0001606=QWERTYU\u0001605=23456\u0001606=ASDFGHJ\u0001600=CDEFG\u0001601=QRST\u0001604=1\u0001605=34567\u0001606=ZXCVBNM\u0001600=DEFGH\u0001601=RSTU\u0001604=1\u0001605=45678\u0001606=POIUYTR\u000110=043\u0001";
        // derived from MessageFactory.MakeNewOrderMultileg(), does not contain fields fields 34, 49, 52, 56 not in quickfixn msg, SeqNum, SendingTime ...
        private string newOrderMultilegBytes = "8=FIX.4.4\u00019=1342\u000135=AB\u00011=PKNOIWA\u000111=PZXWQ\u000112=-79228.162495817593533\u000113=4\u000115=USD\u000121=3\u000122=D\u000123=ALPJNB\u000140=M\u000144=36.893488143124136\u000148=FJXY\u000154=F\u000155=RSWQE\u000158=MGBVZT\u000160=20170122-06:54:00.000\u000163=9\u000164=20170122\u000165=WI\u000170=ZNOSMQBF\u000175=20170122\u000177=C\u000178=0\u000181=0\u000199=79228162477370849459009.748992\u0001100=UYJN\u0001106=GHBFTU\u0001107=QUOJDHBP\u0001110=792281624589241053767102627.85\u0001111=3.689348814312414\u0001114=N\u0001117=KOCDXBP\u0001120=ZTXLMG\u0001121=Y\u0001126=20170122-06:54:00.000\u0001140=-7922816249581759353.2719300611\u0001152=-0.000000003689349\u0001167=TIPS\u0001168=20170122-06:54:00.000\u0001200=201701w1\u0001201=0\u0001202=79228162458924105376.710262785\u0001203=0\u0001206=4\u0001210=-792281.624773708494590\u0001211=-79228162477370849.454714781696\u0001223=-792281.625142643375807\u0001224=20170122\u0001226=1\u0001227=-5534023222112865484.7\u0001228=79228162477.370849433239945\u0001229=20170122\u0001231=-3689348814312413.5939\u0001239=0\u0001240=20170122\u0001255=ESTXRVJK\u0001349=aa\u0001355=aa\u0001376=NHVWAUY\u0001377=Y\u0001386=0\u0001388=6\u0001389=-79228162514264337580659048.449\u0001427=2\u0001432=20170122\u0001453=0\u0001461=WAUIJ\u0001468=1\u0001469=792281.624958175935327\u0001470=IMGKYZT\u0001472=VZNO\u0001479=VZTHIMGU\u0001480=N\u0001481=1\u0001497=N\u0001513=EFJDHS\u0001516=-5534.023223401355674\u0001526=EFZDRSMQ\u0001529=7\u0001541=20170122\u0001543=YSWHLF\u0001544=2\u0001555=0\u0001563=1\u0001581=2\u0001582=4\u0001589=0\u0001590=1\u0001635=H\u0001660=3\u0001691=DRSWQEF\u0001762=JXYCWAL\u0001775=0\u0001836=1\u0001837=2\u0001838=2\u0001840=1\u0001841=1\u0001842=0\u0001843=0\u0001844=1\u0001846=1\u0001847=-3\u0001848=UOSMHBFZ\u0001849=-55.340232221128655\u0001854=1\u0001864=0\u0001873=20170122\u0001874=20170122\u0001875=-2\u0001876=UVZTX\u0001947=CWKL\u000110=017\u0001";

        [Benchmark]
        public string WriteMarketDataRequest()
        {
            return mdr.ToString(); // ToString is how QuickFixN messages write themselves
        }


        [Benchmark]
        public string WriteQuoteStatusRequestMsgWithGroup()
        {
            return qsrG.ToString(); // ToString is how QuickFixN messages write themselves
        }

        [Benchmark]
        public string WriteQuoteStatusRequestWithNestedGroup()
        {
            return qsrNG.ToString(); // ToString is how QuickFixN messages write themselves
        }


        [Benchmark]
        public string WriteNewOrderMultiLeg()
        {
            return noml.ToString(); // ToString is how QuickFixN messages write themselves
        }

        [Benchmark]
        public QuickFix.Message ReadMarketDataRequest()
        {
            var msg = new QuickFix.Message();
            msg.FromString(marketDataRequestBytes, true, dataDictionary, dataDictionary, msgFactory);
            return msg;
        }

        [Benchmark]
        public QuickFix.Message ReadQuoteStatusRequestWithGrp()
        {
            var msg = new QuickFix.Message();
            msg.FromString(quoteStatusRequestWithGrpBytes, true, dataDictionary, dataDictionary, msgFactory);
            return msg;
        }

        [Benchmark]
        public QuickFix.Message ReadQuoteStatusRequestWithNestedGrp()
        {
            var msg = new QuickFix.Message();
            msg.FromString(quoteStatusRequestWithNestedGrpsBytes, true, dataDictionary, dataDictionary, msgFactory);
            return msg;
        }

        [Benchmark]
        public QuickFix.Message ReadNewOrderMultilegBytes()
        {
            var msg = new QuickFix.Message();
            msg.FromString(newOrderMultilegBytes, true, dataDictionary, dataDictionary, msgFactory);
            return msg;
        }

        //// compare with ReadNewOrderMultilegBytes to check dead-code-elimination. DCE does not appear to be happening
        //[Benchmark]
        //public void ReadNewOrderMultilegBytes2()
        //{
        //    var msg = new QuickFix.Message();
        //    msg.FromString(newOrderMultilegBytes, true, dataDictionary, dataDictionary, msgFactory);
        //}

    }

    public class Program
    {
        public static void Main(string[] args)
        {
            BenchmarkRunner.Run<QFBenchmark>(DefaultConfig.Instance.With(Job.RyuJitX64));
        }
    }
}