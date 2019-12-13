package com.elias;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ProxySpiderApplication.class)
class ProxySpiderApplicationTests {
//	@Autowired
//	private ProxyInfoRepository piRep;
//	@Autowired
//	private ProxyInfoService piSer;
//
//	@Test
//	void contextLoads() throws InterruptedException {
//		for (int i = 1; i < 401; i++) {
//			ExecutorService executorService = Executors.newFixedThreadPool(30);
//			try {
//				List<ProxyInfo> list = SpiderProxy.doSpider("http://www.xiladaili.com/gaoni/" + i);
////				List<ProxyInfo> list = SpiderProxy.doSpider("https://www.xicidaili.com/nn/" + i);
//				for (ProxyInfo info : list) {
//					executorService.execute(new Runnable() {
//						@Override
//						public void run() {
//							long ping = TestProxy.testProxy(info);
//							info.setPing(ping);
//							info.setTime(new Date());
//							piSer.insert(info);
//						}
//					});
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			Thread.sleep(3000);
//		}
////		List<ProxyInfo> list = piRep.findAll();
////		for (ProxyInfo proxyInfo : list) {
////			TestProxy.testProxy(proxyInfo);
////		}
//		System.out.println("=============end=============");
//		System.out.println("=============end=============");
//	}

}
