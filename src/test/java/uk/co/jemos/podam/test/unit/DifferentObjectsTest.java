package uk.co.jemos.podam.test.unit;

import java.util.Observable;

import org.junit.Assert;
import org.junit.Test;

import uk.co.jemos.podam.api.DataProviderStrategy;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import uk.co.jemos.podam.test.dto.ImmutableDefaultFieldsPojo;
import uk.co.jemos.podam.test.dto.UnsupportedCollectionInConstructorPojo;

/**
 * @author daivanov
 *
 */
public class DifferentObjectsTest {

	private static final PodamFactory factory = new PodamFactoryImpl();

	@Test
	public void testObservableInstantiation() {
		Observable observable = factory.manufacturePojo(Observable.class);
		Assert.assertNotNull("Manufacturing failed", observable);
	}
	
	@Test
	public void testImmutableDefaultFieldsPojoInstantiation() {
		ImmutableDefaultFieldsPojo model = factory.manufacturePojo(ImmutableDefaultFieldsPojo.class);
		Assert.assertNotNull("Manufacturing failed", model);
		Assert.assertNotNull("List manufacturing failed", model.getList());
		Assert.assertNotNull("Map manufacturing failed", model.getMap());
		DataProviderStrategy strategy = factory.getStrategy();
		Assert.assertEquals("List is not filled",
				strategy.getNumberOfCollectionElements(model.getList().getClass()),
				model.getList().size());
		Assert.assertEquals("Map is not filled",
				strategy.getNumberOfCollectionElements(model.getMap().getClass()),
				model.getMap().size());
	}

	@Test
	public void testUnsupportedCollectionInConstructorPojoInstantiation() {
		UnsupportedCollectionInConstructorPojo<?> pojo =
				factory.manufacturePojo(UnsupportedCollectionInConstructorPojo.class,
						String.class);
		Assert.assertNotNull("Manufacturing failed", pojo);
		Assert.assertNotNull("Collection manufacturing failed", pojo.getVector());
		Assert.assertFalse("Collection is empty", pojo.getVector().isEmpty());
		for (Object element : pojo.getVector()) {
			Assert.assertEquals("Wrong collection element",
					String.class, element.getClass());
		}
	}
}
