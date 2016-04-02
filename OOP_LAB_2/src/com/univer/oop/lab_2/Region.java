package com.univer.oop.lab_2;

public class Region extends Place {
    private String name;
    private MegaCity megaCity;
    private City[] cities;

    public Region(String name, MegaCity megaCity, City[] cities) {
        this.name = name;
        this.megaCity = megaCity;
        this.cities = cities;
    }

    @Override
    public String randClimate() {
        if (Math.random() > 0.5) return "HOT";
        else return "COLD";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MegaCity getMegaCity() {
        return megaCity;
    }

    public void setMegaCity(MegaCity megaCity) {
        this.megaCity = megaCity;
    }

    public City[] getCities() {
        return cities;
    }

    public void setCities(City[] cities) {
        this.cities = cities;
    }

    public void diplay(){
        System.out.println("Регіон: " + name +
                           "\nКлімат регіону: " + randClimate() +
                           "\nКількість міст в регіоні: " + cities.length +
                           "\n\nМегаполіс: " + megaCity.getName() + ", " + megaCity.getPopulation() + ", " + megaCity.getSpeed_limit());

        for(int i = 0; i < cities.length; i++)
            System.out.println(cities[i].getName() + ", " + cities[i].getPopulation());
    }
}
