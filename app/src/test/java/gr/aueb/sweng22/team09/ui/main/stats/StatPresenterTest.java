package gr.aueb.sweng22.team09.ui.main.stats;

import junit.framework.TestCase;

import org.junit.Assert;

import gr.aueb.sweng22.team09.domainlogic.dao.Initializer;
import gr.aueb.sweng22.team09.domainlogic.dao.TestInitializer;
import gr.aueb.sweng22.team09.domainlogic.memorydao.MemoryInitializer;
import gr.aueb.sweng22.team09.domainlogic.memorydao.MemoryTransactionDAO;
import gr.aueb.sweng22.team09.domainlogic.memorydao.MemoryUserDAO;
import gr.aueb.sweng22.team09.domainlogic.memorydao.TestMemoryInitializer;

/**Tests if the Stat Presenter class works as intended
 *
 * @author Ioannis Gkionis
 */
public class StatPresenterTest extends TestCase {

    StatPresenter statpresenter = new StatPresenter(new MemoryUserDAO(), new MemoryTransactionDAO());

    @Override
    public void setUp(){
        TestInitializer initializer = new TestMemoryInitializer();
        initializer.eraseData();
        initializer.prepareData();
    }

    /**Tests whether monthly value of transactions can be calculated correctly
     *
     */
    public void testCalculateMonthlyVOT() {
        Assert.assertEquals("USD 95.00", statpresenter.calculateMonthlyVOT());
    }

    /**Tests whether total value of transactions can be calculated correctly
     *
     */
    public void testCalculateTotalVOT() {
        Assert.assertEquals("USD 200.00", statpresenter.calculateTotalVOT());
    }

    /**Tests if amount of total users can be calculated correctly
     *
     */
    public void testCalculateAmountofUsers() {
        Assert.assertEquals("3", statpresenter.calculateAmountOfUsers());
    }
}