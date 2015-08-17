/*
 * Sonar PL/SQL Plugin (Community)
 * Copyright (C) 2015 Felipe Zorzo
 * felipe.b.zorzo@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package br.com.felipezorzo.sonar.plsql.sql;

import static org.sonar.sslr.tests.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import br.com.felipezorzo.sonar.plsql.api.PlSqlGrammar;
import br.com.felipezorzo.sonar.plsql.api.RuleTest;

public class SelectExpressionTest extends RuleTest {

    @Before
    public void init() {
        setRootRule(PlSqlGrammar.SELECT_EXPRESSION);
    }
    
    @Test
    public void matchesSimpleSelect() {
        assertThat(p).matches("select 1 from dual");
    }
    
    @Test
    public void matchesSimpleSelectInto() {
        assertThat(p).matches("select 1 into var from dual");
    }
    
    @Test
    public void matchesSelectBulkCollectInto() {
        assertThat(p).matches("select 1 bulk collect into var from dual");
    }
    
    @Test
    public void matchesSelectWithWhere() {
        assertThat(p).matches("select 1 from dual where 1 = 1");
    }
    
    @Test
    public void matchesSelectWithMultipleColumns() {
        assertThat(p).matches("select 1, 2 from dual");
    }
    
    @Test
    public void matchesSelectWithMultipleColumnsAndIntoClause() {
        assertThat(p).matches("select 1, 2 into var1, var2 from dual");
    }
    
    @Test
    public void matchesSelectWithMultipleTables() {
        assertThat(p).matches("select 1 from emp, dept");
    }
    
    @Test
    public void matchesSelectAll() {
        assertThat(p).matches("select all 1 from dual");
    }
    
    @Test
    public void matchesSelectDistinct() {
        assertThat(p).matches("select distinct 1 from dual");
    }
    
    @Test
    public void matchesSelectUnique() {
        assertThat(p).matches("select unique 1 from dual");
    }
    
    @Test
    public void matchesSelectWithGroupBy() {
        assertThat(p).matches("select 1 from dual group by 1");
    }
    
    @Test
    public void matchesSelectWithOrderBy() {
        assertThat(p).matches("select 1 from dual order by 1");
    }

    @Test
    public void matchesSelectWithParenthesis() {
        assertThat(p).matches("(select 1 from dual)");
    }
    
    @Test
    public void matchesSelectWithUnion() {
        assertThat(p).matches("select 1 from dual union select 2 from dual");
        assertThat(p).matches("(select 1 from dual) union (select 2 from dual)");
    }
    
    @Test
    public void matchesSelectWithUnionAll() {
        assertThat(p).matches("select 1 from dual union all select 2 from dual");
        assertThat(p).matches("(select 1 from dual) union all (select 2 from dual)");
    }
    
    @Test
    public void matchesSelectWithMinus() {
        assertThat(p).matches("(select 1 from dual) minus (select 2 from dual)");
    }
    
    @Test
    public void matchesSelectWithIntersect() {
        assertThat(p).matches("(select 1 from dual) intersect (select 2 from dual)");
    }
    
    @Test
    public void matchesSelectCountDistinct() {
        assertThat(p).matches("select count(distinct foo) from dual");
    }
    
    @Test
    public void matchesSelectWithAnalyticFunction() {
        assertThat(p).matches("select count(foo) over () from dual");
        assertThat(p).matches("select (count(foo) over ()) from dual");
        assertThat(p).matches("select func(count(foo) over ()) from dual");
    }
    
    @Test
    public void matchesSelectWithAnsiJoin() {
        assertThat(p).matches("select 1 from foo join bar on join.id = bar.id");
    }

}
