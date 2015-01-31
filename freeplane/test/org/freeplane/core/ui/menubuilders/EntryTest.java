package org.freeplane.core.ui.menubuilders;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.Arrays;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.mockito.Mockito;

public class EntryTest {

	@Test
	public void equalEntriesWithChild() {
		Entry firstStructureWithEntry = new Entry();
		final Entry firstEntry = new Entry();
		firstStructureWithEntry.addChild(firstEntry);

		Entry otherStructureWithEntry = new Entry();
		final Entry otherEntry = new Entry();
		otherStructureWithEntry.addChild(otherEntry);
		
		assertThat(firstStructureWithEntry, equalTo(otherStructureWithEntry));
	}

	@Test
	public void equalEntriesWithDifferentBuilders() {
		Entry firstStructureWithEntry = new Entry();
		firstStructureWithEntry.setBuilders(Arrays.asList("builder"));

		Entry otherStructureWithEntry = new Entry();
		assertThat(firstStructureWithEntry, CoreMatchers.not(otherStructureWithEntry));
	}

	@Test
	public void knowsParent() {
		Entry structureWithEntry = new Entry();
		final Entry child = new Entry();
		structureWithEntry.addChild(child);
		
		assertThat(child.getParent(), equalTo(structureWithEntry));
	}


	@Test
	public void rootEntryPathIsSlashifiedName() {
		Entry entry = new Entry();
		entry.setName("name");
		assertThat(entry.getPath(), equalTo("/name"));
	}


	@Test
	public void childEntryPathIsSlashifiedNameAfterParentName() {
		Entry entry = new Entry();
		entry.setName("parent");
		Entry child = new Entry();
		child.setName("child");
		entry.addChild(child);
		assertThat(child.getPath(), equalTo("/parent/child"));
	}

	@Test
	public void iteratesOverSingleChild() {
		Entry firstStructureWithEntry = new Entry();
		final Entry firstEntry = new Entry();
		firstStructureWithEntry.addChild(firstEntry);
		Entry child = null;
		for(Entry childInLoop : firstStructureWithEntry.children())
			child = childInLoop;
		assertThat(firstEntry, equalTo(child));
	}
	
	@Test 
	public void findsParentComponent(){
		Object component = mock(Object.class);
		Entry structureWithEntry = new Entry();
		structureWithEntry.setComponent(component);
		final Entry child = new Entry();
		structureWithEntry.addChild(child);
		
		assertThat(child.getAncestorComponent(), equalTo(component));
	}
	
	@Test 
	public void returnsNullIfAncestorComponentIsNotAvailable(){
		Entry structureWithEntry = new Entry();
		final Entry child = new Entry();
		structureWithEntry.addChild(child);
		
		assertThat(child.getAncestorComponent(), CoreMatchers.<Object>nullValue());
	}
	
	@Test 
	public void findsAncestorComponent(){
		Object component = mock(Object.class);
		Entry structureWithEntry = new Entry();
		structureWithEntry.setComponent(component);
		final Entry level1child = new Entry();
		structureWithEntry.addChild(level1child);
		
		final Entry level2child = new Entry();
		level1child.addChild(level2child);

		assertThat(level2child.getAncestorComponent(), equalTo(component));
	}
}
