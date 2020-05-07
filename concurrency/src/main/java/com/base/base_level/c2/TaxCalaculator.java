package com.base.base_level.c2;

/**
 * @Author:zhao.song
 * @Date:2019/10/16 13:52
 * @Description:
 */
public class TaxCalaculator {

    private Double salary;
    private Double bonus;

    private CalculatorStrategy calCulatorStrategy;

    public TaxCalaculator(Double salary, Double bonus) {
        this.salary = salary;
        this.bonus = bonus;
    }

    protected Double calcTax(){
        return calCulatorStrategy.calculate(salary,bonus);
    }

    public Double calaculate(){
        return this.calcTax();
    }


    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    public void setCalCulatorStrategy(CalculatorStrategy calCulatorStrategy) {
        this.calCulatorStrategy = calCulatorStrategy;
    }
}
