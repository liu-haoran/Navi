// Generated code from Butter Knife. Do not modify!
package com.amap.navi.demo.Blutooth;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ListView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.amap.navi.demo.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Bluetooth_ViewBinding implements Unbinder {
  private Bluetooth target;

  @UiThread
  public Bluetooth_ViewBinding(Bluetooth target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public Bluetooth_ViewBinding(Bluetooth target, View source) {
    this.target = target;

    target.list = Utils.findRequiredViewAsType(source, R.id.bule_view, "field 'list'", ListView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Bluetooth target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.list = null;
  }
}
