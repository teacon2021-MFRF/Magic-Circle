package mfrf.magic_circle.gui.widgets;

import com.mojang.brigadier.arguments.ArgumentType;
import mfrf.magic_circle.gui.engraver_table.EngraverTableScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Argument<T> extends Widget {

    EngraverTableScreen.ArgumentType type;
    public String argumentName;
    public Consumer<T> setter;
    public Supplier<T> getter;
    boolean errorValue = false;

    public Argument(String name, Consumer<T> setter, Supplier<T> getter, EngraverTableScreen.ArgumentType type) {
        super(0, 0, 21, 9, new TranslationTextComponent("magic_circle.gui.argument"));
        this.type = type;
        this.argumentName = name;
        this.setter = setter;
        this.getter = getter;
    }

    public void set(String setting) {
        Object o = type.tryParse(setting);
        if (o == null) {
            errorValue = true;
        } else {
            setter.accept((T) o);
        }
    }

    public T get() {
        return getter.get();
    }


}
