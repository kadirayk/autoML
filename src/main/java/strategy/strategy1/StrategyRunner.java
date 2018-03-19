package strategy.strategy1;

public class StrategyRunner {

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 5; i++) {
			System.out.println("Running strategy1 for AutoML");
			Thread.sleep(1000);
		}
		System.out.println("Strategy is ready");
	}

}
