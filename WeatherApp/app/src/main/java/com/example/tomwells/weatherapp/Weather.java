package com.example.tomwells.weatherapp;

public class Weather {
	
	public Location location;
	public CurrentCondition currentCondition = new CurrentCondition();
	public Temperature temperature = new Temperature();
	public Wind wind = new Wind();
	
	public  class CurrentCondition {

		private String condition;
		private String descr;

		public String getCondition() {
			return condition;
		}
		public void setCondition(String condition) {
			this.condition = condition;
		}
		public String getDescr() {
			return descr;
		}
		public void setDescr(String descr) {
			this.descr = descr;
		}
		
		
	}
	
	public  class Temperature {
		private float temp;
		
		public float getTemp() {
			return temp;
		}
		public void setTemp(float temp) {
			this.temp = temp;
        }
		
	}
	
	public  class Wind {
		private float speed;
		public float getSpeed() {
			return speed;
		}
		public void setSpeed(float speed) {
			this.speed = speed;
		}

	}
}
