package xzcode.ggserver.game.support;

import java.text.DecimalFormat;

public class DisplayUtil {
	DecimalFormat df = new DecimalFormat("#00,000.00");
	public String fenToMoneyYuan(long amount) {
		return df.format(amount);
	}

}
