package com.heaerie.common.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SortMail {

    @Test
    public void test() {

        List<Mail> notInOrder= new ArrayList<>();

        Mail ab =new Mail();
        ab.setSender("A");
        ab.setReceiver("B");

        Mail cd =new Mail();
        cd.setSender("C");
        cd.setReceiver("D");
        Mail bt =new Mail();
        bt.setSender("B");
        bt.setReceiver("T");
        Mail bc =new Mail();
        bc.setSender("B");
        bc.setReceiver("C");

        Mail xy =new Mail();
        xy.setSender("X");
        xy.setReceiver("Y");

        Mail ax =new Mail();
        ax.setSender("A");
        ax.setReceiver("X");

        Mail yz =new Mail();
        yz.setSender("Y");
        yz.setReceiver("Z");

        notInOrder.add(yz);
        notInOrder.add(ab);
        notInOrder.add(cd);
        notInOrder.add(bt);
        notInOrder.add(bc);
        notInOrder.add(xy);
        notInOrder.add(ax);
        System.out.println(rerankCoverPayment(notInOrder, ab));


    }

    private List<Mail>  rerankCoverPayment(List<Mail> notInOrder, Mail ab) {
        List<Mail> newOrderedList = new ArrayList<>();
        List<Mail> newSenderMatched = getSenderMatchedPayments(notInOrder, ab);
        System.out.println("newSenderMatched=" + newSenderMatched);
        if (!newSenderMatched.isEmpty() && !newOrderedList.isEmpty()) {
            for (Mail o: newSenderMatched) {
                if (!newOrderedList.contains(o)) {
                    newOrderedList.add(0, o);
                }
            }
        }

        for (Mail p:
             notInOrder) {



            newSenderMatched = getSenderMatchedPayments(notInOrder, p);
            System.out.println("newSenderMatched=" + newSenderMatched);
            if (!newSenderMatched.isEmpty() && !newOrderedList.isEmpty()) {
                for (Mail o: newSenderMatched) {
                    if (!newOrderedList.contains(o)) {
                        newOrderedList.add(1, o);
                    }
                }
            }
            if (!newOrderedList.contains(p)) {
                System.out.println("add p into newOrderList=" + p);
                newOrderedList.add(p);
            }
            //time orered receiver matched payments
           List<Mail> newReviverMatched=  getReceiverMatchedPayment(notInOrder, p);
            System.out.println("newReviverMatched=" + newReviverMatched);
           if (!newReviverMatched.isEmpty()) {
               for (Mail o: newReviverMatched) {
                   if (!newOrderedList.contains(o)) {
                       newOrderedList.add(o);
                   }
               }
           }
            if (newSenderMatched.isEmpty() && newReviverMatched.isEmpty()) {
                if (!newOrderedList.contains(p)) {
                    System.out.println("add p into newOrderList=" + p);
                    newOrderedList.add(p);
                }
            }
            System.out.println("newOrderedList=" + newOrderedList);

            System.out.println("notInOrder=" + notInOrder);
        }
        return newOrderedList;
    }

    private List<Mail> getSenderMatchedPayments(List<Mail> notInOrder, Mail p) {
      return   notInOrder.stream().filter(np->  np.getReceiver().equals(p.getSender())).collect(Collectors.toList());
    }

    private List<Mail> getReceiverMatchedPayment(List<Mail> notInOrder, Mail p) {
        return   notInOrder.stream().filter(np-> np.getSender().equals(p.getReceiver())).collect(Collectors.toList());
    }

}
