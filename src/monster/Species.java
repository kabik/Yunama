package monster;

public enum Species {
	TSUCHI("土"), KOKE("コケ"), TSUBOMI("ツボミ"), HANA("ハナ"), MUSHI("ムシ");

	private final String name;

	Species(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}