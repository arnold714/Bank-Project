package MiniProject.Record;

import java.sql.Date;

public class Record {
	//레코드 번호
	private int num; 
	//계좌번호
	private String account_num;
	//입금,출금액
	private int money;
	//입금,출금자명
	private String name; 
	//금액(잔액)
	private int balnace;
	//거래일
	private Date newDate; 
	//거래 구분: 예금 or 출금
	private int isDeposit; //1이면 입금 ,2이면 출금
	
	private String id;


	public Record() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Record(int num, String  account_num, int money, String name, int balnace, Date newDate, int isDeposit,String id) {
		super();
		this.num = num;
		this.account_num = account_num;
		this.money = money;
		this.name = name;
		this.balnace = balnace;
		this.newDate = newDate;
		this.isDeposit = isDeposit;
		this.id = id;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String  getAccount_num() {
		return account_num;
	}

	public void setAccount_num(String  account_num) {
		this.account_num = account_num;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBalnace() {
		return balnace;
	}

	public void setBalnace(int balnace) {
		this.balnace = balnace;
	}

	public Date getNewDate() {
		return newDate;
	}

	public void setNewDate(Date newDate) {
		this.newDate = newDate;
	}
	
	public int getIsDeposit() {
		return isDeposit;
	}

	public void setIsDeposit(int isDeposit) {
		this.isDeposit = isDeposit;
	}
	

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	@Override
	public String toString() {
		if(isDeposit==1) {
			return num+". "+newDate+ "/"+name+"이 "+account_num+"번 계좌에 "+ money+"원 입금/ 잔액: "+balnace;
		}else {
			return num+". "+newDate+ "/"+name+"에게 "+account_num+"번 계좌에서 "+ money+"원 출금/ 잔액: "+balnace;
			}
		
	}


} 