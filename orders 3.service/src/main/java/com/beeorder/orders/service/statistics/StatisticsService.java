package com.beeorder.orders.service.statistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties.Simple;
import com.beeorder.orders.service.order.Order;
import com.beeorder.orders.service.order.OrdersInventory;
import com.beeorder.orders.service.order.SimpleOrder;
import com.beeorder.orders.service.order.orderComponent;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StatisticsService {
    HashMap<String, Integer> phoneFrequencyMap = new HashMap<>();
    HashMap<String, Integer> mailFrequencyMap = new HashMap<>();
    public Statistics statistics = new Statistics();
    @Autowired
    OrdersInventory ordersInventory = new OrdersInventory();// to get the statistics from
        List<orderComponent> ordersFromInv = ordersInventory.orders;

    public void calcStatPhone() {
        // check if it is simple then directly get the phone and email
        for (orderComponent order : ordersFromInv) {
            if (order instanceof SimpleOrder) {
                SimpleOrder tempOrder = new SimpleOrder();
                tempOrder = (SimpleOrder) order;
                System.out.println(tempOrder.orderAccount.getPhone());
                String phoneNum = tempOrder.orderAccount.getPhone();
                if (phoneFrequencyMap.containsKey(phoneNum)) {
                    // Increment the frequency if the number is already present
                    phoneFrequencyMap.put(phoneNum, phoneFrequencyMap.get(phoneNum) + 1);
                } else {
                    // Add the number to the map with a frequency of 1 if it's not present
                    phoneFrequencyMap.put(phoneNum, 1);
                }
            }
            else
            {
        // else if it is compound order loop on its simple orders and get their phone
        // and email
        Order temp = (Order) order;
        List<SimpleOrder> oSimpleOrders = temp.getOrderComponents();
        for(orderComponent ord : oSimpleOrders)
        {
            SimpleOrder o = (SimpleOrder) ord;
            String phoneNum = o.orderAccount.getPhone();
                if (phoneFrequencyMap.containsKey(phoneNum)) {
                    // Increment the frequency if the number is already present
                    phoneFrequencyMap.put(phoneNum, phoneFrequencyMap.get(phoneNum) + 1);
                } else {
                    // Add the number to the map with a frequency of 1 if it's not present
                    phoneFrequencyMap.put(phoneNum, 1);
                }
        }
            }
        }
    }

    public void calcStatEmail() {
        for (orderComponent order : ordersFromInv) {
            if (order instanceof SimpleOrder) {
                SimpleOrder tempOrder = new SimpleOrder();
                tempOrder = (SimpleOrder) order;
                System.out.println("toty" +tempOrder.orderAccount.getEmail());
                String mail = tempOrder.orderAccount.getEmail();

                if (mailFrequencyMap.containsKey(mail)) {
                    // Increment the frequency if the number is already present
                    mailFrequencyMap.put(mail, mailFrequencyMap.get(mail) + 1);
                } else {
                    // Add the number to the map with a frequency of 1 if it's not present
                    mailFrequencyMap.put(mail, 1);
                }
            }
            else
            {
        // else if it is compound order loop on its simple orders and get their phone
        // and email
        Order temp = (Order) order;
        List<SimpleOrder> oSimpleOrders = temp.getOrderComponents();
        for(orderComponent ord : oSimpleOrders)
        {
            SimpleOrder o = (SimpleOrder) ord;
            String mail = o.orderAccount.getEmail();
                if (mailFrequencyMap.containsKey(mail)) {
                    // Increment the frequency if the number is already present
                    mailFrequencyMap.put(mail, mailFrequencyMap.get(mail) + 1);
                } else {
                    // Add the number to the map with a frequency of 1 if it's not present
                    mailFrequencyMap.put(mail, 1);
                }
        }
            }
        }
    }
    public String getMax()
    {
        calcStatEmail();
        calcStatPhone();
        String maxPhone = null;
        int maxPhoneFrequency = Integer.MIN_VALUE;

        for (Map.Entry<String, Integer> entry : phoneFrequencyMap.entrySet()) {
            if (entry.getValue() > maxPhoneFrequency) {
                maxPhone = entry.getKey();
                maxPhoneFrequency = entry.getValue();
            }
        }
        statistics.mostFreqPhone = maxPhone;
        // Find email with the highest frequency
        String maxEmail = null;
        int maxEmailFrequency = Integer.MIN_VALUE;

        for (Map.Entry<String, Integer> entry : mailFrequencyMap.entrySet()) {
            if (entry.getValue() > maxEmailFrequency) {
                maxEmail = entry.getKey();
                maxEmailFrequency = entry.getValue();
            }
        }
        statistics.mostFreqMail = maxEmail;
        // Display phone number and email with the highest frequency
        System.out.println("Phone number with highest frequency: " + maxPhone + " (" + maxPhoneFrequency + " times)");
        System.out.println("Email with highest frequency: " + maxEmail + " (" + maxEmailFrequency + " times)");

        String result = "Most notified Phone number : " + statistics.mostFreqPhone + "\nMost notified Email: "+statistics.mostFreqMail;
        return result;
    }
    }

