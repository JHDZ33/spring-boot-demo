package com.xkcoding.orm.mybatis.list;

import com.xkcoding.orm.mybatis.entity.DataEntity;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * stream相关查看根目录下的“常用stream.md”
 */
public class StreamTest {

    /**
     * map
     */
    @Test
    public void mapTest() {
        // 0. 操作String字符串list
        List<String> stringList = Arrays.asList("a", "b", "c", "d");
        List<String> collect = stringList.stream().map(String::toUpperCase).collect(Collectors.toList());
        System.out.println("**0** " + collect.toString());

        // 1. 操作IntegerList
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4);
        List<Integer> collect1 = integerList.stream().map(n -> n * 2).map(n -> n * 2).collect(Collectors.toList());
        System.out.println("**1** " + collect1.toString());

        // 2. 操作对象
        List<DataEntity> dataList = getDataList(5);
        List<Long> collect2 = dataList.stream().map(DataEntity::getId).collect(Collectors.toList());
        List<Long> collect21 = dataList.stream().map(dataEntity -> {
            dataEntity.setId(dataEntity.getId() * 10);
            return dataEntity;
        }).map(DataEntity::getId).collect(Collectors.toList());
        System.out.println("**2** " + collect2);
        System.out.println("**21** " + collect21);
    }

    /**
     * flatMap
     * 总而言之，flatMap 操作可以将多个 Stream 扁平化为一个 Stream，从而方便集合数据的处理。
     */
    @Test
    public void flatMapTest() {
        // 想把字符串数组中出现的所有字符打印，不能重复
        String[] strings= {"up!up", "end", "end", "Elden", "Ring"};
        // 如果使用map，字符串经过spilt后所有的所有字符各自成为一个Stream，然后成为Stream<Stream<String>>
        Arrays.stream(strings).map(s -> s.split("")).map(Arrays::stream).distinct().forEach(stringStream -> stringStream.forEach(System.out::println));

        // 使用flatMap,会将所有经过spilt的字符合并为一个stream，然后进行总处理
        Arrays.stream(strings).map(s -> s.split("")).flatMap(Arrays::stream).distinct().forEach(System.out::println);
    }

    /**
     * list转map
     */
    @Test
    public void listToMapTest(){
        List<DataEntity> dataList = getDataList(5);
        //  (key1, key2) -> key2)  表示如果生成的map key重复的情况下，取后者
        Map<Long, String> collect1 = dataList.stream().collect(Collectors.toMap(DataEntity::getId, DataEntity::getName, (key1, key2) -> key2));
        // Function.identity()表示list中的元素
        Map<Long, DataEntity> collect2 = dataList.stream().collect(Collectors.toMap(DataEntity::getId, Function.identity(), (key1, key2) -> key2));
        System.out.println("**1** " + collect1.toString() + "\n");
        System.out.println("**2** " + collect2.toString() + "\n");
    }

    /**
     * filter
     *
     * findAny和findFirst在少量数据串行下是一样的，返回第一个符合条件的数据，如果是并行则findAny就可能返回不同的值了
     */
    @Test
    public void filterTest() {
        List<DataEntity> dataList = getDataList(5);
        DataEntity entity = new DataEntity();
        entity.setId(0);
        entity.setName("repeat0");
        entity.setNow(new Date());
        dataList.add(entity);
        DataEntity dataEntity1 = dataList.stream().filter(dataEntity -> dataEntity.getId() == 0).findFirst().orElse(null);
        DataEntity dataEntity2 = dataList.stream().filter(dataEntity -> dataEntity.getName().startsWith("r")).findAny().orElse(null);
        List<DataEntity> dataEntity3 = dataList.stream().filter(dataEntity -> dataEntity.getId() == 0).collect(Collectors.toList());
        System.out.println(dataEntity1 == null ? "" : dataEntity1.toString());
        System.out.println(dataEntity2 == null ? "" : dataEntity2.toString());
        System.out.println(dataEntity3.toString());
        List<String> stringList = Arrays.asList("aa", "aba", "ca", "da");
        List<String> collect = stringList.stream().filter(s -> s.startsWith("a")).collect(Collectors.toList());
        System.out.println(collect.toString());
    }

    /**
     * match
     * 其实就是filter的缩写版本，只返回true or false
     * allMatch：Stream 中全部元素符合传入的 predicate，返回 true
     * anyMatch：Stream 中只要有一个元素符合传入的 predicate，返回 true
     * noneMatch：Stream 中没有一个元素符合传入的 predicate，返回 true
     * 它们都不是要遍历全部元素才能返回结果。例如 allMatch 只要一个元素不满足条件，就 skip 剩下的所有元素，返回 false。
     */
    @Test
    public void matchTest() {
        List<DataEntity> dataList = getDataList(5);
        boolean present = dataList.stream().filter(dataEntity -> dataEntity.getId() == 0).findAny().isPresent();
        System.out.println(present);
        boolean b = dataList.stream().anyMatch(dataEntity -> dataEntity.getId() == 0);
        System.out.println(b);

        List<Long> condition = new ArrayList<>();
        condition.add(1L);
        boolean b1 = dataList.stream().map(DataEntity::getId).anyMatch(condition::contains);
        System.out.println(b1);
    }

    /**
     * foreach(terminal 操作)
     */
    @Test
    public void foreachTest() {
        List<DataEntity> dataList = getDataList(5);
        dataList.stream().filter(dataEntity -> dataEntity.getId() <= 2).forEach(dataEntity -> System.out.println(dataEntity.getName()));
    }

    /**
     * ofNullable( terminal 兼 short-circuiting 操作)
     */
    @Test
    public void ofNullableTest() {
        String text1 = "";
        String text2 = null;
        String text3 = "abc";
        Optional.ofNullable(text3).ifPresent(System.out::println);
        Integer integer = Optional.ofNullable(text3).map(String::length).orElse(-1);
        System.out.println(integer);
    }

    /**
     * reduce
     * 它提供一个起始值（种子），然后依照运算规则（BinaryOperator），和前面 Stream 的第一个、第二个、第 n 个元素组合。
     */
    @Test
    public void reduceTest() {
        String reduce = Stream.of("A", "B", "C", "D").reduce("", String::concat);
        System.out.println(reduce);

        Double reduce1 = Stream.of(-1.0, -22.0, -430.0, 2.0).reduce(Double.MAX_VALUE, Double::min);
        System.out.println(reduce1);

        Integer reduce2 = Stream.of(1, 2, 55, 66, 999).reduce(0, Integer::sum);
        // 求和，sumValue = 10, 无起始值，返回Optional,所以有get()方法
        Integer reduce3 = Stream.of(1, 2, 55, 66, 999).reduce(Integer::sum).get();
        System.out.println(reduce2 + "***" + reduce3);

        String s1 = Stream.of("a", "B", "C", "d", "e", "f", "G").filter(s -> s.compareTo("Z") > 0).reduce(String::concat).get();
        System.out.println(s1);
    }

    /**
     * limit and skip
     */
    @Test
    public void limitAndSkipTest() {
        List<DataEntity> dataList = getDataList(100);
        // 像这种map情况尽管list长度是100，但因为limit参数为10，所以实际getName执行次数仅为10
        // 如果使用sort这种，则执行次数就不会因limit而减少了
        List<String> collect = dataList.stream().map(DataEntity::getName).limit(10).collect(Collectors.toList());
        System.out.println(collect.toString());

        List<String> collect1 = collect.stream().skip(4).collect(Collectors.toList());
        System.out.println(collect1);
    }

    /**
     * sort
     * 对 Stream 的排序通过 sorted 进行，它比数组的排序更强之处在于你可以首先对 Stream 进行各类 map、filter、limit、skip 甚至 distinct 来减少元素数量后，再排序，这能帮助程序明显缩短执行时间。
     */
    @Test
    public void sortTest() {
        List<DataEntity> dataList = getDataList(10);
        List<String> collect = dataList.stream().map(DataEntity::getName).limit(5).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        System.out.println(collect.toString());
    }

    /**
     * distinct and max and min
     */
    @Test
    public void minAndMaxAndDistinctTest() {
        List<DataEntity> dataList = getDataList(10);
        DataEntity dataEntity = new DataEntity();
        dataEntity.setId(10);
        dataEntity.setName("name1");
        dataEntity.setNow(new Date());
        dataList.add(dataEntity);
        // 去重
        List<String> collect = dataList.stream().map(DataEntity::getName).distinct().collect(Collectors.toList());
        System.out.println(collect.toString());
        // 这个也能用sort进行实现，但这样成本更低
        Long aLong = dataList.stream().map(DataEntity::getId).max(Long::compareTo).get();
        System.out.println(aLong);
    }

    /**
     * generate
     */
    @Test
    public void generateTest() {
        // 生成100以内的随机int
        Random seed = new Random();
        Supplier<Integer> integerCopier = () -> seed.nextInt(100);
        Stream.generate(integerCopier).limit(10).forEach(System.out::println);

        // 当然下面这个匿名内部类也可以摘出来写个实现Supplier的方法
        Supplier<DataEntity> dataEntityCopier = () -> {
            DataEntity dataEntity = new DataEntity();
            dataEntity.setName("name" + seed.nextInt(10));
            return dataEntity;
        };
        Stream.generate(dataEntityCopier).limit(10).forEach(System.out::println);
    }

    /**
     * iterate
     */
    @Test
    public void iterateTest() {
        // 生成等差数列
        Stream.iterate(0,n -> n + 3).limit(10).forEach(System.out::println);
    }

    /**
     * groupingBy
     */
    @Test
    public void groupingByTest() {
        // 通过groupingBy对生成的list以id进行分组
        Random random = new Random();
        Supplier<DataEntity> dataEntityCopier = () -> {
            DataEntity dataEntity = new DataEntity();
            dataEntity.setId(random.nextInt(10));
            dataEntity.setName("name" + random.nextInt(10));
            dataEntity.setNow(new Date());
            return dataEntity;
        };
        Map<Long, List<DataEntity>> collect = Stream.generate(dataEntityCopier).limit(100).collect(Collectors.groupingBy(DataEntity::getId));
        Iterator<Map.Entry<Long, List<DataEntity>>> iterator = collect.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, List<DataEntity>> next = iterator.next();
            System.out.println("id" + next.getKey() + "=" + next.getValue().toString() + "\n");
        }
    }

    /**
     * partitioningBy (partitioning分割)
     */
    @Test
    public void partitioningByTest() {
        List<DataEntity> dataList = getDataList(20);
        Map<Boolean, List<DataEntity>> collect = dataList.stream().collect(Collectors.partitioningBy(p -> p.getId() < 10));
        collect.get(true).stream().forEach(System.out::println);
        System.out.println("*****分割*****");
        collect.get(false).stream().forEach(System.out::println);
    }


    @Test
    public void comparedToTest() {
        String a = "name1";
        String b = "name4";
        System.out.println(a.compareTo(b));
    }

    /**
     *
     * @param count
     * @return
     */
    private List<DataEntity> getDataList(int count) {
        List<DataEntity> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            DataEntity dataEntity = new DataEntity();
            dataEntity.setId(i);
            dataEntity.setName("name" + i);
            dataEntity.setNow(new Date());
            list.add(dataEntity);
        }
        return list;
    }
}
