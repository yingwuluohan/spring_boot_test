package com.unisound.iot.controller.jdk.lambad.lambda_function;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ListLambda {

    private static List< Integer > list = Arrays.asList( 1,2,3,4,5,6,7,9 );

    /**
     * 过滤
     * @return
     */
    public static List< Integer> foreach(){
        List< Integer > result = list.stream().filter( x -> x > 5 ).limit( 2 ).collect( toList() );

        System.out.println( result );
        return result;
    }

    /**
     * 映射
     *
     */
    public static void getMap(){
        Map< Integer , User> map = list.stream().collect( Collectors.toMap( v -> v , User::new ));

        System.out.println( "map : " + map );

    }

    /**
     * 排序
     * @param
     */
    public List< User > getSortUser( List<User > userList ){
        List< User > list = userList.stream().sorted( Comparator.comparing( User::getNum )).collect( Collectors.toList()) ;
        return  list;
    }

    /**
     * flatMap
     * @param
     */
    public List< User > getMapUser(List<List< User > > userList){
        List< User > list = userList.stream().flatMap( user ->  user.stream() ).collect(toList());
        return list;
    }

    public static Map getMapU(){
        Map<Integer,String> map = new HashMap<>();
        map.put( 1 , "aa" );
        map.put( 2 , "bb" );
        map.put(3 , "cc" );
        List< User > userList = new ArrayList<>();
        userList.add( new User( 1 , "a" ));
        userList.add( new User( 2 , "b" ));
        userList.add(  new User( 3 , "" ));

         userList.stream().forEach(
                user -> {
                    boolean flag = StringUtils.isNotBlank(map.get(user.getId())) && StringUtils.isBlank(user.getName());
                    System.out.println( flag );
                    if(flag){
                        System.out.println("UserId:"+ user.getId() );
                        user.setName(map.get(user.getId()));
                    }
                }
        );
        System.out.println("userList:"+ userList );
        Map<Integer,User> result = userList.stream().collect(Collectors.toMap(User::getId, Function.identity(),(oldVal, newVal)->oldVal ));
        Map<Integer,User> result2 = userList.stream().collect(Collectors.toMap(User::getId, Function.identity() ));

        System.out.println( "result:"+ result );
        System.out.println( "result:"+ result2 );

        map.forEach(
                (fId,defaultVal) -> {
                    User fieldDataRequest = result2.get(fId);
                    System.out.println( "defalutVal:" + defaultVal );
                    if(null == fieldDataRequest){
                        User users = User.builder().id(fId).name(defaultVal).build();
                        userList.add( users );
                    }
                }
        );
        System.out.println( map );
        return map;
    }

    /** List 遍历*/
    public void getbatchList(){
        int pageNum = 1;
        int pageSize = 10;
        SearchHits searchHits = null;

                List<Integer> dataIdList =  Arrays.stream(searchHits.getHits())
                .map(SearchHit::getId)
                .map(Integer::valueOf)
                .skip((pageNum - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }


    public static void main(String[] args) {
//        foreach();
//        getMap();
        getMapU();
    }

}
