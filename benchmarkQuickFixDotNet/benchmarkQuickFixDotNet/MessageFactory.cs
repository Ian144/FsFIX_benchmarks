﻿using QuickFix.FIX44;
using QuickFix.Fields;



namespace BenchmarkRW
{
    public static class MessageFactory
    {
        public static QuoteStatusRequest MakeQuoteStatusRequestIncGroup()
        {
            var msg = new QuoteStatusRequest();
            msg.Symbol = new Symbol("ABCDE");
            var legsGroup = new QuoteStatusRequest.NoLegsGroup();
            legsGroup.LegSymbol = new LegSymbol("BCDEG");
            legsGroup.LegSymbolSfx = new LegSymbolSfx("PQRS");
            msg.AddGroup(legsGroup); // legsGroup is cloned before being mutated
            legsGroup.LegSymbol = new LegSymbol("CDEFG");
            legsGroup.LegSymbolSfx = new LegSymbolSfx("QRST");
            msg.AddGroup(legsGroup);
            legsGroup.LegSymbol = new LegSymbol("DEFGH");
            legsGroup.LegSymbolSfx = new LegSymbolSfx("RSTU");
            msg.AddGroup(legsGroup);
            return msg;
        }


        public static QuoteStatusRequest MakeQuoteStatusRequestIncNestedGroup()
        {
            var msg = new QuoteStatusRequest();
            msg.Symbol = new Symbol("ABCDE");

            var legsGroup = new QuoteStatusRequest.NoLegsGroup();
            legsGroup.LegSymbol = new LegSymbol("BCDEG");
            legsGroup.LegSymbolSfx = new LegSymbolSfx("PQRS");
            var legSecAltId = new QuoteStatusRequest.NoLegsGroup.NoLegSecurityAltIDGroup();
            legSecAltId.LegSecurityAltIDSource = new LegSecurityAltIDSource("QUERTYU");
            legsGroup.AddGroup(legSecAltId);
            legSecAltId.LegSecurityAltIDSource = new LegSecurityAltIDSource("ASDFGHJ");
            legsGroup.AddGroup(legSecAltId);

            msg.AddGroup(legsGroup); // groups are cloned before being mutated
            legsGroup.LegSymbol = new LegSymbol("CDEFG");
            legsGroup.LegSymbolSfx = new LegSymbolSfx("QRST");
            legSecAltId.LegSecurityAltIDSource = new LegSecurityAltIDSource("ZXCVBNM");
            legsGroup.AddGroup(legSecAltId);

            msg.AddGroup(legsGroup);
            legsGroup.LegSymbol = new LegSymbol("DEFGH");
            legsGroup.LegSymbolSfx = new LegSymbolSfx("RSTU");
            legSecAltId.LegSecurityAltIDSource = new LegSecurityAltIDSource("POIUYTR");
            legsGroup.AddGroup(legSecAltId);

            msg.AddGroup(legsGroup);

            return msg;
        }


        public static MarketDataRequest MakeMarketDataRequest()
        {
            //let ms = System.DateTimeOffset.Now.ToUnixTimeMilliseconds()
            //let mdr = MkMarketDataRequest(
            //            MDReqID(sprintf "MDRQ-%d" ms),
            //            SubscriptionRequestType.SnapshotPlusUpdates,
            //            MarketDepth 1,
            //            [MDEntryType.Bid; MDEntryType.Offer] |> List.map MkNoMDEntryTypesGrp,
            //            [MkInstrument(Fix44.Fields.Symbol "EUR/USD")] |> List.map MkMarketDataRequestNoRelatedSymGrp
            //            )

            var msg = new MarketDataRequest();
            //var ms = System.DateTimeOffset.Now.ToUnixTimeMilliseconds();
            var ms = 1489416392996;
            msg.MDReqID = new MDReqID($"MDRQ-{ms}");
            msg.SubscriptionRequestType = new SubscriptionRequestType(SubscriptionRequestType.SNAPSHOT_PLUS_UPDATES);
            msg.MarketDepth = new MarketDepth(1);

            var entryTypesGroup = new MarketDataRequest.NoMDEntryTypesGroup();
            entryTypesGroup.MDEntryType = new MDEntryType(MDEntryType.BID);
            msg.AddGroup(entryTypesGroup);
            entryTypesGroup.MDEntryType = new MDEntryType(MDEntryType.OFFER);
            msg.AddGroup(entryTypesGroup);

            var symGroup = new MarketDataRequest.NoRelatedSymGroup();
            symGroup.Symbol = new Symbol("EUR/USD");
            msg.AddGroup(symGroup);
            return msg;
        }

        public static NewOrderMultileg MakeNewOrderMultileg()
        {

            string localMktDate = "20170122";
            var utcTimeStamp = new System.DateTime(2017, 01, 22, 06, 54, 00);

            var msg = new NewOrderMultileg();

            // instrument component fields
            msg.Symbol = new Symbol("RSWQE");       // odd values were generated by FsCheck
            msg.SymbolSfx = new SymbolSfx(SymbolSfx.WHEN_ISSUED);
            msg.SecurityID = new SecurityID("FJXY");
            msg.SecurityIDSource = new SecurityIDSource(SecurityIDSource.VALOREN);
            msg.CFICode = new CFICode("WAUIJ");
            msg.SecurityType = new SecurityType(SecurityType.TREASURY_INFLATION_PROTECTED_SECURITIES);
            msg.SecuritySubType = new SecuritySubType("JXYCWAL");
            msg.MaturityMonthYear = new MaturityMonthYear("201701w1"); // 
            msg.MaturityDate = new MaturityDate(localMktDate);
            msg.PutOrCall = new PutOrCall(PutOrCall.PUT);
            msg.CouponPaymentDate = new CouponPaymentDate(localMktDate);
            msg.RepoCollateralSecurityType = new RepoCollateralSecurityType(0);
            msg.RepurchaseTerm = new RepurchaseTerm(1);
            msg.RepurchaseRate = new RepurchaseRate(-5534023222112865484.7M);  // odd values generated by FsCheck
            msg.Factor = new Factor(79228162477.370849433239945M);
            msg.CreditRating = new CreditRating("ESTXRVJK");
            msg.InstrRegistry = new InstrRegistry("YSWHLF");
            msg.CountryOfIssue = new CountryOfIssue("IMGKYZT");
            msg.LocaleOfIssue = new LocaleOfIssue("VZNO");
            msg.RedemptionDate = new RedemptionDate(localMktDate);
            msg.StrikePrice = new StrikePrice(79228162458924105376.710262785M);
            msg.StrikeCurrency = new StrikeCurrency("CWKL");
            msg.OptAttribute = new OptAttribute('4');
            msg.ContractMultiplier = new ContractMultiplier(-3689348814312413.5939M);
            msg.CouponRate = new CouponRate(-792281.625142643375807M);
            msg.Issuer = new Issuer("GHBFTU");
            msg.EncodedIssuer = new EncodedIssuer("aa");
            msg.SecurityDesc = new SecurityDesc("QUOJDHBP");
            msg.Pool = new Pool("DRSWQEF");
            msg.CPProgram = new CPProgram(-2);
            msg.CPRegType = new CPRegType("UVZTX");
            //msg.NoEvents = new NoEvents(0);
            msg.DatedDate = new DatedDate(localMktDate);
            msg.InterestAccrualDate = new InterestAccrualDate(localMktDate);

            // msg fields
            msg.ClOrdID = new ClOrdID("PZXWQ");
            msg.SecondaryClOrdID = new SecondaryClOrdID("EFZDRSMQ");
            //msg.NoPartyIDs = new NoPartyIDs(0);
            msg.TradeOriginationDate = new TradeOriginationDate(localMktDate);
            msg.TradeDate = new TradeDate(localMktDate);
            msg.Account = new Account("PKNOIWA");
            msg.AcctIDSource = new AcctIDSource(AcctIDSource.TFM);
            msg.AccountType = new AccountType(AccountType.ACCOUNT_IS_CARRIED_ON_NON_CUSTOMER_SIDE_OF_BOOKS);
            msg.DayBookingInst = new DayBookingInst(DayBookingInst.CAN_TRIGGER_BOOKING_WITHOUT_REFERENCE_TO_THE_ORDER_INITIATOR);
            msg.BookingUnit = new BookingUnit(BookingUnit.AGGREGATE_PARTIAL_EXECUTIONS_ON_THIS_ORDER_AND_BOOK_ONE_TRADE_PER_ORDER);
            msg.AllocID = new AllocID("ZNOSMQBF");
            //msg.NoAllocs = new NoAllocs(0);
            msg.SettlType = new SettlType(SettlType.T_PLUS_5);
            msg.SettlDate = new SettlDate(localMktDate);
            msg.CashMargin = new CashMargin(CashMargin.MARGIN_OPEN);
            msg.ClearingFeeIndicator = new ClearingFeeIndicator(ClearingFeeIndicator.FIRMS_106H_AND_106J);
            msg.HandlInst = new HandlInst(HandlInst.MANUAL_ORDER);
            msg.MinQty = new MinQty(792281624589241053767102627.85M);
            msg.MaxFloor = new MaxFloor(3.689348814312414M);
            msg.ExDestination = new ExDestination("UYJN");
            //msg.NoTradingSessions = new NoTradingSessions(0);
            msg.ProcessCode = new ProcessCode(ProcessCode.REGULAR);
            msg.Side = new Side(Side.LEND);

            msg.PrevClosePx = new PrevClosePx(-7922816249581759353.2719300611M);
            //msg.NoLegs = new NoLegs(0);
            msg.LocateReqd = new LocateReqd(false);
            msg.TransactTime = new TransactTime(utcTimeStamp);
            msg.QtyType = new QtyType(QtyType.CONTRACTS);

            // orderQty component
            msg.CashOrderQty = new CashOrderQty(-0.000000003689349M);
            msg.OrderPercent = new OrderPercent(-5534.023223401355674M);
            msg.RoundingDirection = new RoundingDirection(RoundingDirection.ROUND_DOWN);
            msg.RoundingModulus = new RoundingModulus(792281.624958175935327M);  // a randomly chosen value, that is bonkers but should be parseable

            // msg fields
            msg.OrdType = new OrdType(OrdType.NEXT_FUND_VALUATION_POINT);
            msg.Price = new Price(36.893488143124136M);
            msg.StopPx = new StopPx(79228162477370849459009.748992M);
            msg.Currency = new Currency("USD");
            msg.ComplianceID = new ComplianceID("NHVWAUY");
            msg.SolicitedFlag = new SolicitedFlag(true);
            msg.IOIid = new IOIid("ALPJNB");


            msg.QuoteID = new QuoteID("KOCDXBP");
            msg.EffectiveTime = new EffectiveTime(utcTimeStamp);
            msg.ExpireDate = new ExpireDate(localMktDate);
            msg.ExpireTime = new ExpireTime(utcTimeStamp);
            msg.GTBookingInst = new GTBookingInst(GTBookingInst.ACCUMULATE_UNTIL_VERBALLY_NOTIFIED_OTHERWISE);
            msg.Commission = new Commission(-79228.162495817593533M);
            msg.CommType = new CommType(CommType.PERCENTAGE_WAIVED_CASH_DISCOUNT);
            msg.CommCurrency = new CommCurrency("VZTHIMGU");
            msg.FundRenewWaiv = new FundRenewWaiv(FundRenewWaiv.NO);
            msg.OrderRestrictions = new OrderRestrictions(OrderRestrictions.FOREIGN_ENTITY);
            msg.CustOrderCapacity = new CustOrderCapacity(CustOrderCapacity.ALL_OTHER);
            msg.ForexReq = new ForexReq(true);
            msg.SettlCurrency = new SettlCurrency("ZTXLMG");
            msg.BookingType = new BookingType(BookingType.REGULAR_BOOKING);
            msg.Text = new Text("MGBVZT");
            msg.EncodedText = new EncodedText("aa");
            msg.PositionEffect = new PositionEffect(PositionEffect.CLOSE);
            msg.CoveredOrUncovered = new CoveredOrUncovered(CoveredOrUncovered.COVERED);
            msg.MaxShow = new MaxShow(-792281.624773708494590M);

            // PegInstructions component
            msg.PegOffsetValue = new PegOffsetValue(-79228162477370849.454714781696M);
            msg.PegOffsetType = new PegOffsetType(PegOffsetType.BASIS_POINTS);
            msg.PegLimitType = new PegLimitType(PegLimitType.OR_WORSE);
            msg.PegRoundDirection = new PegRoundDirection(PegRoundDirection.MORE_PASSIVE);

            // DiscressionInstructions component
            msg.PegScope = new PegScope(PegScope.LOCAL);
            msg.DiscretionInst = new DiscretionInst(DiscretionInst.RELATED_TO_VWAP);
            msg.DiscretionOffsetValue = new DiscretionOffsetValue(-79228162514264337580659048.449M);
            msg.DiscretionMoveType = new DiscretionMoveType(DiscretionMoveType.FIXED);
            msg.DiscretionOffsetType = new DiscretionOffsetType(DiscretionOffsetType.PRICE);
            msg.DiscretionLimitType = new DiscretionLimitType(DiscretionLimitType.OR_BETTER);
            msg.DiscretionRoundDirection = new DiscretionRoundDirection(DiscretionRoundDirection.MORE_AGGRESSIVE);
            msg.DiscretionScope = new DiscretionScope(DiscretionScope.LOCAL);
            msg.TargetStrategy = new TargetStrategy(-3);
            msg.TargetStrategyParameters = new TargetStrategyParameters("UOSMHBFZ");
            msg.ParticipationRate = new ParticipationRate(-55.340232221128655M);
            msg.CancellationRights = new CancellationRights(CancellationRights.NO_EXECUTION_ONLY);
            msg.MoneyLaunderingStatus = new MoneyLaunderingStatus(MoneyLaunderingStatus.EXEMPT_BELOW_THE_LIMIT);
            msg.RegistID = new RegistID("EFJDHS");
            msg.MultiLegRptTypeReq = new MultiLegRptTypeReq(MultiLegRptTypeReq.REPORT_BY_MULTILEG_SECURITY_AND_BY_INSTRUMENT_LEGS_BELONGING_TO_THE_MULTILEG_SECURITY);

            return msg;
        }
    }
}