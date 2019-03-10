//package cuj.loganalyst.service.handle.common.merge;
//
//import org.junit.Test;
//
//import java.util.LinkedList;
//import java.util.List;
//
///**
// * @Auther: cujamin
// * @Date: 2018/11/1 15:50
// * @Description:
// */
//public class MergeServiceImplTest {
//
//    @Test
//    public void handle() {
//    }
//
//    @Test
//    public void handle1() {
//    }
//
//
//    @Test
//    public void mergeTwoList(){
//        mergeTwoList0();
//        mergeTwoList1();
//        mergeTwoListN();
//    }
//
//    public void mergeTwoList0(){
//        System.out.println("-------------0-----------------------");
//        MergeServiceImpl mergeService = new MergeServiceImpl();
//        List<String> dataListA = new LinkedList<String>();
//        List<String> dataListB = new LinkedList<String>();
//
//        List<String> outList = mergeService.mergeTwoList(dataListA,dataListB);
//        for(String temp :outList) {
//            System.out.println(temp);
//        }
//    }
//
//    public void mergeTwoList1(){
//        System.out.println("-------------1-----------------------");
//        MergeServiceImpl mergeService = new MergeServiceImpl();
//        String SAMPLEA = "2018-10-31 11:32:44,3%d1 INFO[outCall-pool-4] -%s";
//        String SAMPLEB = "2018-10-31 11:32:44,3%d7 INFO[outCall-pool-4] -%s";
//        List<String> dataListA = new LinkedList<String>();
//        List<String> dataListB = new LinkedList<String>();
//        int nA = 4;
//        for(int i=0;i<=nA;++i){
//            dataListA.add(String.format(SAMPLEA, i,"A"));
//            dataListB.clear();
//            for(int j=0;j<=i;++j){
//                dataListB.add(String.format(SAMPLEB, j,"B"));
//
//                System.out.println("--------------A:"+(i+1)+" ; B:"+(j+1)+"------------");
//                List<String> outList = mergeService.mergeTwoList(dataListA,dataListB);
//                for(String temp :outList){
//                    System.out.println(temp);
//                }
//            }
//        }
//    }
//
//    public void mergeTwoListN() {
//        System.out.println("-------------N-----------------------");
//        int n=1;
//        String SAMPLEA = "2018-10-31 11:32:44,3%d1 INFO[outCall-pool-4]";
//        String SAMPLEB = "2018-10-31 11:32:44,3%d7 INFO[outCall-pool-4]";
//
//        List<String> dataListA = new LinkedList<String>();
//        for(int i=0;i<1;++i){
//            dataListA.add(String.format(SAMPLEA, i));
//        }
//
//        List<String> dataListB = new LinkedList<String>();
//        for(int i=1;i<2;++i){
//            dataListB.add(String.format(SAMPLEB, i));
//        }
//
//        MergeServiceImpl mergeService = new MergeServiceImpl();
//        List<String> list = mergeService.mergeTwoList(dataListA,dataListB);
//        for(String temp :list){
//            System.out.println(temp);
//        }
//    }
//}