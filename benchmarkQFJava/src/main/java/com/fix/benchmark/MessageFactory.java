package com.fix.benchmark;

import quickfix.field.*;
import quickfix.fix44.MarketDataRequest;
import quickfix.fix44.NewOrderMultileg;
import quickfix.fix44.QuoteStatusRequest;
import quickfix.fix44.component.Instrument;

/**
 * Created by Ian on 07/03/2017.
 */

class MessageFactory {

    static MarketDataRequest MakeMarketDataRequest()
    {
        MarketDataRequest msg = new MarketDataRequest();

        msg.set( new MDReqID("MDRQ-" + String.valueOf(System.currentTimeMillis())) );
        msg.set( new SubscriptionRequestType(SubscriptionRequestType.SNAPSHOT_PLUS_UPDATES) );
        msg.set( new MarketDepth(1) );

        MarketDataRequest.NoMDEntryTypes entryTypesGroup = new MarketDataRequest.NoMDEntryTypes();
        entryTypesGroup.set(new MDEntryType(MDEntryType.BID));
        msg.addGroup(entryTypesGroup);
        entryTypesGroup.set( new MDEntryType(MDEntryType.OFFER));
        msg.addGroup(entryTypesGroup);

        MarketDataRequest.NoRelatedSym symGroup = new MarketDataRequest.NoRelatedSym();
        symGroup.set(new Symbol("EUR/USD"));
        msg.addGroup(symGroup);
        return msg;
    }


    static QuoteStatusRequest MakeQuoteStatusRequestIncGroup()
    {
        QuoteStatusRequest msg = new QuoteStatusRequest();
        msg.set( new Symbol("ABCDE") );

        QuoteStatusRequest.NoLegs legsGroup = new QuoteStatusRequest.NoLegs();
        legsGroup.set(new LegSymbol("BCDEG"));
        legsGroup.set(new LegSymbolSfx("PQRS"));
         msg.addGroup(legsGroup); // legsGroup is cloned before being mutated CHECK THIS IS TRUE FOR QFJ??

        legsGroup.set(new LegSymbol("CDEFG"));
        legsGroup.set(new LegSymbolSfx("QRST"));
        msg.addGroup(legsGroup);

        legsGroup.set(new LegSymbol("DEFGH"));
        legsGroup.set(new LegSymbolSfx("RSTU"));
        msg.addGroup(legsGroup);

        return msg;
    }

    static QuoteStatusRequest MakeQuoteStatusRequestIncNestedGroup()
    {
        QuoteStatusRequest msg = new QuoteStatusRequest();
        msg.set (new Symbol("ABCDE"));

        QuoteStatusRequest.NoLegs legsGroup = new QuoteStatusRequest.NoLegs();
        legsGroup.set( new LegSymbol("BCDEG") );
        legsGroup.set( new LegSymbolSfx("PQRS") );
        QuoteStatusRequest.NoLegs.NoLegSecurityAltID legSecAltId = new QuoteStatusRequest.NoLegs.NoLegSecurityAltID();
        legSecAltId.set( new LegSecurityAltIDSource("QUERTYU") );
        legsGroup.addGroup(legSecAltId);
        legSecAltId.set( new LegSecurityAltIDSource("ASDFGHJ") );
        legsGroup.addGroup(legSecAltId);

        msg.addGroup(legsGroup); // groups are cloned before being mutated
        legsGroup.set( new LegSymbol("CDEFG") );
        legsGroup.set( new LegSymbolSfx("QRST") );
        legSecAltId.set( new LegSecurityAltIDSource("ZXCVBNM") );
        legsGroup.addGroup(legSecAltId);

        msg.addGroup(legsGroup);
        legsGroup.set( new LegSymbol("DEFGH") );
        legsGroup.set( new LegSymbolSfx("RSTU") );
        legSecAltId.set( new LegSecurityAltIDSource("POIUYTR") );
        legsGroup.addGroup(legSecAltId);

        msg.addGroup(legsGroup);

        return msg;
    }




    static NewOrderMultileg MakeNewOrderMultileg()
    {

        String localMktDate = "20170122";
        @SuppressWarnings("deprecation")
        java.util.Date utcTimeStamp = new java.util.Date(2017, 01, 22, 06, 54, 00);

        NewOrderMultileg msg = new NewOrderMultileg();
        Instrument inst = new Instrument();
        // instrument component fields
        inst.set(  new Symbol("RSWQE") );       // odd values were generated by FsCheck
        inst.set(  new SymbolSfx(SymbolSfx.WHEN_ISSUED));
        inst.set(  new SecurityID("FJXY"));
        inst.set(  new SecurityIDSource(SecurityIDSource.VALOREN));
        inst.set(  new CFICode("WAUIJ"));
        inst.set(  new SecurityType(SecurityType.TREASURY_INFLATION_PROTECTED_SECURITIES));
        inst.set(  new SecuritySubType("JXYCWAL"));
        inst.set(  new MaturityMonthYear("201701w1")) ;
        inst.set(  new MaturityDate(localMktDate));
        inst.set(  new PutOrCall(PutOrCall.PUT));
        inst.set(  new CouponPaymentDate(localMktDate));
        inst.set(  new RepoCollateralSecurityType("0"));
        inst.set(  new RepurchaseTerm(1));
        inst.set(  new RepurchaseRate(-5534023222112865484.7));  // odd values generated by FsCheck
        inst.set(  new Factor(79228162477.370849433239945));
        inst.set(  new CreditRating("ESTXRVJK"));
        inst.set(  new InstrRegistry("YSWHLF"));
        inst.set(  new CountryOfIssue("IMGKYZT"));
        inst.set(  new LocaleOfIssue("VZNO"));
        inst.set(  new RedemptionDate(localMktDate));
        inst.set(  new StrikePrice(79228162458924105376.710262785));
        inst.set(  new StrikeCurrency("CWKL"));
        inst.set(  new OptAttribute('4'));
        inst.set(  new ContractMultiplier(-3689348814312413.5939));
        inst.set(  new CouponRate(-792281.625142643375807));
        inst.set(  new Issuer("GHBFTU"));
        inst.set(  new EncodedIssuer("aa"));
        inst.set(  new SecurityDesc("QUOJDHBP"));
        inst.set(  new Pool("DRSWQEF"));
        inst.set(  new CPProgram(-2));
        inst.set(  new CPRegType("UVZTX"));
        //inst.set(  new NoEvents(0));
        inst.set(  new DatedDate(localMktDate));
        inst.set(  new InterestAccrualDate(localMktDate));

        msg.set(inst);

        // msg fields
        msg.set(  new ClOrdID("PZXWQ"));
        msg.set(  new SecondaryClOrdID("EFZDRSMQ"));
        //msg.set(  new NoPartyIDs(0));
        msg.set(  new TradeOriginationDate(localMktDate));
        msg.set(  new TradeDate(localMktDate));
        msg.set(  new Account("PKNOIWA"));
        msg.set(  new AcctIDSource(AcctIDSource.TFM));
        msg.set(  new AccountType(AccountType.ACCOUNT_IS_CARRIED_ON_NON_CUSTOMER_SIDE_OF_BOOKS));
        msg.set(  new DayBookingInst(DayBookingInst.CAN_TRIGGER_BOOKING_WITHOUT_REFERENCE_TO_THE_ORDER_INITIATOR));
        msg.set(  new BookingUnit(BookingUnit.AGGREGATE_PARTIAL_EXECUTIONS_ON_THIS_ORDER_AND_BOOK_ONE_TRADE_PER_ORDER));
        msg.set(  new AllocID("ZNOSMQBF"));
        //msg.set(  new NoAllocs(0));
        msg.set(  new SettlType(SettlType.T_PLUS_5));
        msg.set(  new SettlDate(localMktDate));
        msg.set(  new CashMargin(CashMargin.MARGIN_OPEN));
        msg.set(  new ClearingFeeIndicator(ClearingFeeIndicator.FIRMS_106H_AND_106J));
        msg.set(  new HandlInst(HandlInst.MANUAL_ORDER));
        msg.set(  new MinQty(792281624589241053767102627.85));
        msg.set(  new MaxFloor(3.689348814312414));
        msg.set(  new ExDestination("UYJN"));
        //msg.set(  new NoTradingSessions(0));
        msg.set(  new ProcessCode(ProcessCode.REGULAR));
        msg.set(  new Side(Side.LEND));

        msg.set(  new PrevClosePx(-7922816249581759353.2719300611));
        //msg.set(  new NoLegs(0));
        msg.set(  new LocateReqd(false));
        msg.set(  new TransactTime(utcTimeStamp));
        msg.set(  new QtyType(QtyType.CONTRACTS));

        // orderQty component
        msg.set(  new CashOrderQty(-0.000000003689349));
        msg.set(  new OrderPercent(-5534.023223401355674));
        msg.set(  new RoundingDirection(RoundingDirection.ROUND_DOWN));
        msg.set(  new RoundingModulus(792281.624958175935327) );  // a randomly chosen value, that is bonkers but should be parseable

        // msg fields
        msg.set(  new OrdType(OrdType.NEXT_FUND_VALUATION_POINT));
        msg.set(  new Price(36.893488143124136));
        msg.set(  new StopPx(79228162477370849459009.748992));
        msg.set(  new Currency("USD"));
        msg.set(  new ComplianceID("NHVWAUY"));
        msg.set(  new SolicitedFlag(true));
        msg.set(  new IOIID("ALPJNB"));


        msg.set(  new QuoteID("KOCDXBP"));
        msg.set(  new EffectiveTime(utcTimeStamp));
        msg.set(  new ExpireDate(localMktDate));
        msg.set(  new ExpireTime(utcTimeStamp));
        msg.set(  new GTBookingInst(GTBookingInst.ACCUMULATE_UNTIL_VERBALLY_NOTIFIED_OTHERWISE));
        msg.set(  new Commission(-79228.162495817593533));
        msg.set(  new CommType(CommType.PERCENTAGE_WAIVED_CASH_DISCOUNT));
        msg.set(  new CommCurrency("VZTHIMGU"));
        msg.set(  new FundRenewWaiv(FundRenewWaiv.NO));
        //msg.set(  new OrderRestrictions(OrderRestrictions.FOREIGN_ENTITY));
        msg.set(  new OrderRestrictions("7")); // the line above does not compile, oddly, OrderRestrictions expects a string, of value "7" in this case
        msg.set(  new CustOrderCapacity(CustOrderCapacity.ALL_OTHER));
        msg.set(  new ForexReq(true));
        msg.set(  new SettlCurrency("ZTXLMG"));
        msg.set(  new BookingType(BookingType.REGULAR_BOOKING));
        msg.set(  new Text("MGBVZT"));
        msg.set(  new EncodedText("aa"));
        msg.set(  new PositionEffect(PositionEffect.CLOSE));
        msg.set(  new CoveredOrUncovered(CoveredOrUncovered.COVERED));
        msg.set(  new MaxShow(-792281.624773708494590));

        // PegInstructions component
        msg.set(  new PegOffsetValue(-79228162477370849.454714781696));
        msg.set(  new PegOffsetType(PegOffsetType.BASIS_POINTS));
        msg.set(  new PegLimitType(PegLimitType.OR_WORSE));
        msg.set(  new PegRoundDirection(PegRoundDirection.MORE_PASSIVE));

        // DiscressionInstructions component
        msg.set(  new PegScope(PegScope.LOCAL));
        msg.set(  new DiscretionInst(DiscretionInst.RELATED_TO_VWAP));
        msg.set(  new DiscretionOffsetValue(-79228162514264337580659048.449));
        msg.set(  new DiscretionMoveType(DiscretionMoveType.FIXED));
        msg.set(  new DiscretionOffsetType(DiscretionOffsetType.PRICE));
        msg.set(  new DiscretionLimitType(DiscretionLimitType.OR_BETTER));
        msg.set(  new DiscretionRoundDirection(DiscretionRoundDirection.MORE_AGGRESSIVE));
        msg.set(  new DiscretionScope(DiscretionScope.LOCAL));
        msg.set(  new TargetStrategy(-3));
        msg.set(  new TargetStrategyParameters("UOSMHBFZ"));
        msg.set(  new ParticipationRate(-55.340232221128655));
        msg.set(  new CancellationRights(CancellationRights.NO_EXECUTION_ONLY));
        msg.set(  new MoneyLaunderingStatus(MoneyLaunderingStatus.EXEMPT_BELOW_THE_LIMIT));
        msg.set(  new RegistID("EFJDHS"));
        msg.set(  new MultiLegRptTypeReq(MultiLegRptTypeReq.REPORT_BY_MULTILEG_SECURITY_AND_BY_INSTRUMENT_LEGS_BELONGING_TO_THE_MULTILEG_SECURITY));

        return msg;
    }
}
