package com.jetbrains.teamsys.dnq.database;

import com.jetbrains.mps.dnq.common.tests.AbstractEntityStoreAwareTestCase;
import static com.jetbrains.mps.dnq.common.tests.TestOnlyServiceLocator.getTransientEntityStore;
import jetbrains.exodus.database.Entity;
import jetbrains.exodus.database.TransientEntity;
import jetbrains.exodus.database.TransientEntityStore;
import jetbrains.exodus.database.TransientStoreSession;
import com.jetbrains.teamsys.dnq.association.DirectedAssociationSemantics;
import com.jetbrains.teamsys.dnq.association.PrimitiveAssociationSemantics;
import jetbrains.mps.internal.collections.runtime.ListSequence;

/**
 * Date: 14.12.2006
 * Time: 14:26:16
 *
 * @author Vadim.Gurov
 */
public class DisposeCursorTest extends AbstractEntityStoreAwareTestCase {

  public void testDisposeCursor() throws Exception {
    // 1

    createUsers();

    _login_LoginForm_handler();
  }

  public void testDisposeOnLastElement() {
    createUsers();

    findUser("1", "1");
  }

  public int _login_LoginForm_handler() {
    TransientEntityStore store = getTransientEntityStore();
    TransientStoreSession session = store.beginSession("_login");
    try {

      Entity user = ((TransientStoreSession) getTransientEntityStore().getThreadSession().getCurrentTransaction()).addSessionLocalEntity(
              "user", (TransientEntity)(TestUserService.findUser("vadim", "vadim")));

      return 1;

    } catch (Throwable e) {
      TransientStoreUtil.abort(e, session);
      throw new RuntimeException();
    } finally {
      TransientStoreUtil.commit(session);
    }
  }

  public void createUsers() {
    TransientEntityStore store = getTransientEntityStore();
    TransientStoreSession session = store.beginSession("_login");
    try {

      if(ListSequence.fromIterable(getTransientEntityStore().getThreadSession().getAll("User")).size() > 0) {
        return;
      }

      Entity u = ((TransientStoreSession) getTransientEntityStore().getThreadSession()).addSessionLocalEntity("u", getTransientEntityStore().getThreadSession().newEntity("User"));
      PrimitiveAssociationSemantics.set(u, "username", (Comparable)"vadim");
      PrimitiveAssociationSemantics.set(u, "password", (Comparable)"vadim");
      Entity i = ((TransientStoreSession) getTransientEntityStore().getThreadSession()).addSessionLocalEntity("i", getTransientEntityStore().getThreadSession().newEntity("Issue"));
      DirectedAssociationSemantics.setToOne(i, "reporter", (Entity)u);
      PrimitiveAssociationSemantics.set(i, "summary", (Comparable)"test issue");

    } catch (Throwable e) {
      TransientStoreUtil.abort(e, session);
      throw new RuntimeException();
    } finally {
      TransientStoreUtil.commit(session);
    }
  }

  public static void findUser(String username, String password) {
    TransientEntityStore store = getTransientEntityStore();
    TransientStoreSession session = store.beginSession("_login");
    try {

      Iterable<Entity> users = getTransientEntityStore().getThreadSession().find("User", "login", username).intersect(getTransientEntityStore().getThreadSession().find("User", "password", password));
      if(!(ListSequence.fromIterable(users).isEmpty())) {
        System.out.println("found");
      }

    } catch (Throwable e) {
      TransientStoreUtil.abort(e, session);
      throw new RuntimeException();
    } finally {
      TransientStoreUtil.commit(session);
    }

  }

}
