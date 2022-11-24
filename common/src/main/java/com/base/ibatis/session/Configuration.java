package com.base.ibatis.session;

import com.base.ibatis.builder.mapper.XMLStatementBuilder;
import com.base.ibatis.mapping.MappedStatement;
import com.base.ibatis.parsing.XNode;
import com.base.ibatis.scripting.LanguageDriver;
import com.base.ibatis.scripting.xmltags.XMLLanguageDriver;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * description  Configuration <BR>
 * <p>
 * author: zhao.song
 * date: created in 14:04  2022/9/20
 * company: TRS信息技术有限公司
 * version 1.0
 */
public class Configuration {

    protected String databaseId;

    protected Properties variables = new Properties();

    protected final Map<String, XNode> sqlFragments = new StrictMap<>("XML fragments parsed from previous mappers");

    //不完整的SQL语句
    protected final Collection<XMLStatementBuilder> incompleteStatements = new LinkedList<>();

    //映射的语句,存在Map里
    protected final Map<String, MappedStatement> mappedStatements = new StrictMap<MappedStatement>("Mapped Statements collection")
            .conflictMessageProducer((savedValue, targetValue) ->
                    ". please check " + savedValue.getResource() + " and " + targetValue.getResource());

    public String getDatabaseId() {
        return databaseId;
    }

    public Properties getVariables() {
        return variables;
    }

    public void setVariables(Properties variables) {
        this.variables = variables;
    }


    public Map<String, XNode> getSqlFragments() {
        return sqlFragments;
    }

    public Collection<XMLStatementBuilder> getIncompleteStatements() {
        return incompleteStatements;
    }

    public void addIncompleteStatement(XMLStatementBuilder incompleteStatement) {
        incompleteStatements.add(incompleteStatement);
    }

    public boolean isShrinkWhitespacesInSql() {
        return false;
    }

    public LanguageDriver getLanguageDriver(){
        return new XMLLanguageDriver();
    }

    public void addMappedStatment(MappedStatement ms) {
        mappedStatements.put(ms.getId(), ms);
    }

    public Collection<String> getMappedStatementNames(String currentNameSpace) {
        buildAllStatements();
        return mappedStatements.keySet().stream()
                .filter(stmtIdName -> !stmtIdName.contains(currentNameSpace))
                .collect(Collectors.toList());
//        return mappedStatements.keySet();
    }

    public Collection<MappedStatement> getMappedStatements(String currentNameSpace) {
        buildAllStatements();
        return mappedStatements.keySet().stream()
                .filter(stmtIdName -> !stmtIdName.contains(currentNameSpace))
                .map(mappedStatements::get)
                .collect(Collectors.toList());
    }

    public MappedStatement getMappedStatement(String id) {
        return this.getMappedStatement(id, true);
    }

    public MappedStatement getMappedStatement(String id, boolean validateIncompleteStatements) {
        //先构建所有语句，再返回语句
        if (validateIncompleteStatements) {
            buildAllStatements();
        }
        return mappedStatements.get(id);
    }

    protected void buildAllStatements() {
//        parsePendingResultMaps();
//        if (!incompleteCacheRefs.isEmpty()) {
//            synchronized (incompleteCacheRefs) {
//                incompleteCacheRefs.removeIf(x -> x.resolveCacheRef() != null);
//            }
//        }
        if (!incompleteStatements.isEmpty()) {
            synchronized (incompleteStatements) {
                incompleteStatements.removeIf(x -> {
                    x.parseStatementNode();
                    return true;
                });
            }
        }
//        if (!incompleteMethods.isEmpty()) {
//            synchronized (incompleteMethods) {
//                incompleteMethods.removeIf(x -> {
//                    x.resolve();
//                    return true;
//                });
//            }
//        }
    }



    //静态内部类,严格的Map，不允许多次覆盖key所对应的value
    protected static class StrictMap<V> extends HashMap<String, V> {

        private static final long serialVersionUID = -4950446264854982944L;
        private final String name;
        private BiFunction<V, V, String> conflictMessageProducer;

        public StrictMap(String name, int initialCapacity, float loadFactor) {
            super(initialCapacity, loadFactor);
            this.name = name;
        }

        public StrictMap(String name, int initialCapacity) {
            super(initialCapacity);
            this.name = name;
        }

        public StrictMap(String name) {
            super();
            this.name = name;
        }

        public StrictMap(String name, Map<String, ? extends V> m) {
            super(m);
            this.name = name;
        }

        /**
         * Assign a function for producing a conflict error message when contains value with the same key.
         * <p>
         * function arguments are 1st is saved value and 2nd is target value.
         * @param conflictMessageProducer A function for producing a conflict error message
         * @return a conflict error message
         * @since 3.5.0
         */
        public StrictMap<V> conflictMessageProducer(BiFunction<V, V, String> conflictMessageProducer) {
            this.conflictMessageProducer = conflictMessageProducer;
            return this;
        }

        @Override
        @SuppressWarnings("unchecked")
        public V put(String key, V value) {
            // 如果已经包含了该key，则直接返回异常
            if (containsKey(key)) {
                throw new IllegalArgumentException(name + " already contains value for " + key
                        + (conflictMessageProducer == null ? "" : conflictMessageProducer.apply(super.get(key), value)));
            }
            if (key.contains(".")) {
                // 按照"."将key切分成数组，并将数组的最后一项作为shortKey
                final String shortKey = getShortName(key);
                if (super.get(shortKey) == null) {
                    // 如果不包含指定shortKey,则添加该键值对
                    super.put(shortKey, value);
                } else {
                    // 如果该shortKey已经存在，则将value修改成Ambiguity对象
                    super.put(shortKey, (V) new Ambiguity(shortKey));
                }
            }
            // 如果不包含该key，则添加该键值对
            return super.put(key, value);
        }

        @Override
        public V get(Object key) {
            V value = super.get(key);
            // 如果该key没有对应的value，则报错
            if (value == null) {
                throw new IllegalArgumentException(name + " does not contain value for " + key);
            }
            // 如果value是Ambiguity类型，则报错
            if (value instanceof Ambiguity) {
                throw new IllegalArgumentException(((Ambiguity) value).getSubject() + " is ambiguous in " + name
                        + " (try using the full name including the namespace, or rename one of the entries)");
            }
            return value;
        }

        protected static class Ambiguity {
            private final String subject;

            public Ambiguity(String subject) {
                this.subject = subject;
            }

            public String getSubject() {
                return subject;
            }
        }

        //取得短名称，也就是取得最后那个句号的后面那部分
        private String getShortName(String key) {
            final String[] keyParts = key.split("\\.");
            return keyParts[keyParts.length - 1];
        }
    }
}
