package com.binance.api.client.impl;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.config.BinanceApiConfig;
import com.binance.api.client.constant.BinanceApiConstants;
import com.binance.api.client.domain.account.*;
import com.binance.api.client.domain.account.request.*;
import com.binance.api.client.domain.general.Asset;
import com.binance.api.client.domain.general.ExchangeInfo;
import com.binance.api.client.domain.market.*;
import retrofit2.Call;

import java.util.List;

/**
 * Implementation of Binance's REST API using Retrofit with synchronous/blocking method calls.
 */
public class BinanceApiRestClientImpl implements BinanceApiRestClient {

  private final BinanceApiService binanceApiService;
  private final BinanceApiConfig config;
  private final BinanceApiServiceGenerator generator;

  public BinanceApiRestClientImpl(BinanceApiServiceGenerator generator, BinanceApiConfig config) {
    this.generator = generator;
	this.config = config;
	binanceApiService = generator.createService(BinanceApiService.class, config);
  }

  // General endpoints

  @Override
  public void ping() {
    generator.executeSync(binanceApiService.ping());
  }

  @Override
  public Long getServerTime() {
    return generator.executeSync(binanceApiService.getServerTime()).getServerTime();
  }

  @Override
  public ExchangeInfo getExchangeInfo() {
    return generator.executeSync(binanceApiService.getExchangeInfo());
  }

  @Override
  public List<Asset> getAllAssets() {
    return generator.executeSync(binanceApiService.getAllAssets(config.getAssetInfoApiBaseUrl() + "assetWithdraw/getAllAsset.html"));
  }

  // Market Data endpoints

  @Override
  public OrderBook getOrderBook(String symbol, Integer limit) {
    return generator.executeSync(binanceApiService.getOrderBook(symbol, limit));
  }

  @Override
  public List<TradeHistoryItem> getTrades(String symbol, Integer limit) {
    return generator.executeSync(binanceApiService.getTrades(symbol, limit));
  }

  @Override
  public List<TradeHistoryItem> getHistoricalTrades(String symbol, Integer limit, Long fromId) {
    return generator.executeSync(binanceApiService.getHistoricalTrades(symbol, limit, fromId));
  }

  @Override
  public List<AggTrade> getAggTrades(String symbol, String fromId, Integer limit, Long startTime, Long endTime) {
    return generator.executeSync(binanceApiService.getAggTrades(symbol, fromId, limit, startTime, endTime));
  }

  @Override
  public List<AggTrade> getAggTrades(String symbol) {
    return getAggTrades(symbol, null, null, null, null);
  }

  @Override
  public List<Candlestick> getCandlestickBars(String symbol, CandlestickInterval interval, Integer limit, Long startTime, Long endTime) {
    return generator.executeSync(binanceApiService.getCandlestickBars(symbol, interval.getIntervalId(), limit, startTime, endTime));
  }

  @Override
  public List<Candlestick> getCandlestickBars(String symbol, CandlestickInterval interval) {
    return getCandlestickBars(symbol, interval, null, null, null);
  }

  @Override
  public TickerStatistics get24HrPriceStatistics(String symbol) {
    return generator.executeSync(binanceApiService.get24HrPriceStatistics(symbol));
  }

  @Override
  public List<TickerStatistics> getAll24HrPriceStatistics() {
	return 	generator.executeSync(binanceApiService.getAll24HrPriceStatistics());
  }

  @Override
  public TickerPrice getPrice(String symbol) {
	  return generator.executeSync(binanceApiService.getLatestPrice(symbol));
  }

  @Override
  public List<TickerPrice> getAllPrices() {
    return generator.executeSync(binanceApiService.getLatestPrices());
  }

  @Override
  public List<BookTicker> getBookTickers() {
    return generator.executeSync(binanceApiService.getBookTickers());
  }

  @Override
  public NewOrderResponse newOrder(NewOrder order) {
    final Call<NewOrderResponse> call;
    if (order.getQuoteOrderQty() == null) {
      call = binanceApiService.newOrder(order.getSymbol(), order.getSide(), order.getType(),
          order.getTimeInForce(), order.getQuantity(), order.getPrice(),
          order.getNewClientOrderId(), order.getStopPrice(), order.getIcebergQty(), order.getNewOrderRespType(),
          order.getRecvWindow(), order.getTimestamp());
    } else {
      call = binanceApiService.newOrderQuoteQty(order.getSymbol(), order.getSide(), order.getType(),
          order.getTimeInForce(), order.getQuoteOrderQty(), order.getPrice(),
          order.getNewClientOrderId(), order.getStopPrice(), order.getIcebergQty(), order.getNewOrderRespType(),
          order.getRecvWindow(), order.getTimestamp());
    }
    return generator.executeSync(call);
  }

  @Override
  public void newOrderTest(NewOrder order) {
	  generator.executeSync(binanceApiService.newOrderTest(order.getSymbol(), order.getSide(), order.getType(),
        order.getTimeInForce(), order.getQuantity(), order.getPrice(), order.getNewClientOrderId(), order.getStopPrice(),
        order.getIcebergQty(), order.getNewOrderRespType(), order.getRecvWindow(), order.getTimestamp()));
  }

  // Account endpoints

  @Override
  public Order getOrderStatus(OrderStatusRequest orderStatusRequest) {
    return generator.executeSync(binanceApiService.getOrderStatus(orderStatusRequest.getSymbol(),
        orderStatusRequest.getOrderId(), orderStatusRequest.getOrigClientOrderId(),
        orderStatusRequest.getRecvWindow(), orderStatusRequest.getTimestamp()));
  }

  @Override
  public CancelOrderResponse cancelOrder(CancelOrderRequest cancelOrderRequest) {
    return generator.executeSync(binanceApiService.cancelOrder(cancelOrderRequest.getSymbol(),
        cancelOrderRequest.getOrderId(), cancelOrderRequest.getOrigClientOrderId(), cancelOrderRequest.getNewClientOrderId(),
        cancelOrderRequest.getRecvWindow(), cancelOrderRequest.getTimestamp()));
  }

  @Override
  public List<Order> getOpenOrders(OrderRequest orderRequest) {
    return generator.executeSync(binanceApiService.getOpenOrders(orderRequest.getSymbol(), orderRequest.getRecvWindow(), orderRequest.getTimestamp()));
  }

  @Override
  public List<Order> getAllOrders(AllOrdersRequest orderRequest) {
    return generator.executeSync(binanceApiService.getAllOrders(orderRequest.getSymbol(),
        orderRequest.getOrderId(), orderRequest.getLimit(),
        orderRequest.getRecvWindow(), orderRequest.getTimestamp()));
  }

  @Override
  public Account getAccount(Long recvWindow, Long timestamp) {
    return generator.executeSync(binanceApiService.getAccount(recvWindow, timestamp));
  }

  @Override
  public Account getAccount() {
    return getAccount(BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis());
  }

  @Override
  public List<Trade> getMyTrades(String symbol, Integer limit, Long fromId, Long recvWindow, Long timestamp) {
    return generator.executeSync(binanceApiService.getMyTrades(symbol, limit, fromId, recvWindow, timestamp));
  }

  @Override
  public List<Trade> getMyTrades(String symbol, Integer limit) {
    return getMyTrades(symbol, limit, null, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis());
  }

  @Override
  public List<Trade> getMyTrades(String symbol) {
    return getMyTrades(symbol, null, null, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis());
  }

  @Override
  public WithdrawResult withdraw(String asset, String address, String amount, String name, String addressTag) {
    return generator.executeSync(binanceApiService.withdraw(asset, address, amount, name, addressTag, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis()));
  }

  @Override
  public DepositHistory getDepositHistory(String asset) {
    return generator.executeSync(binanceApiService.getDepositHistory(asset, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis()));
  }

  @Override
  public WithdrawHistory getWithdrawHistory(String asset) {
    return generator.executeSync(binanceApiService.getWithdrawHistory(asset, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis()));
  }

  @Override
  public DepositAddress getDepositAddress(String asset) {
    return generator.executeSync(binanceApiService.getDepositAddress(asset, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis()));
  }

  // User stream endpoints

  @Override
  public String startUserDataStream() {
    return generator.executeSync(binanceApiService.startUserDataStream()).toString();
  }

  @Override
  public void keepAliveUserDataStream(String listenKey) {
	  generator.executeSync(binanceApiService.keepAliveUserDataStream(listenKey));
  }

  @Override
  public void closeUserDataStream(String listenKey) {
	  generator.executeSync(binanceApiService.closeAliveUserDataStream(listenKey));
  }
}
