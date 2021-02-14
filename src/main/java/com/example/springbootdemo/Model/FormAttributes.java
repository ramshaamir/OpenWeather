package com.example.springbootdemo.Model;

public class FormAttributes
{
  private String city;

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }




  public FormAttributes(String city) {
    super();
    this.city = city;
  }

  public FormAttributes() {
    super();
  }
}
