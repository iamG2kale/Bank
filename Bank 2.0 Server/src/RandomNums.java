import java.io.Serializable;
import java.util.Random;
import java.util.ArrayList;

public class RandomNums implements Serializable {
	
	public RandomNums() {
		list = new int[1000];
	}
	
	public int getNew() {
	
		int n = 0;
		boolean flag = true;
		
		while(flag) {
			flag = false;
			n = new Random().nextInt(8999)+1000;

			for(int i = 0; i < list.length; i++ ) {
				if(list[i] == n) {
					flag = true;
				}
			}
		}
		
		if(empty != 0) {
			list[empty] = n;
			empty = 0;
		}
		else {
			list[accs] = n;
		}
		
		return n;
	}
	
	public void remove(int n) {
		for(int i = 0; i < list.length; i++) {
			if(list[i] == n) {
				list[i] = 0;
				empty = i;
				break;
			}
		}
	}
	
	int[] list;
	int accs = 0;
	int empty = 0;
	
	
}