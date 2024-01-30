/**
 * Spring Data JPA 使用 @OneToOne，@ManyToOne，@JoinColum
 * 等相关注解时，该字段使用懒加载，那么必须开启事务 @Transaction。<br/>
 * 1. 如果在事务上下文之外访问懒加载字段，会出现懒加载异常（LazyInitializationException）。<br/>
 * 2. 如果使用了懒加载，但没开启事务，访问懒加载字段，会出现懒加载异常（LazyInitializationException）。<br/>
 * 3. 只有在开启事务，并且在事务内，访问懒加载字段，才能成功。
 *
 * @author Jeong Geol
 */
package cn.cnowse.jpa;