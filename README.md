# andersen-task4
В проект добавлены кастомная вью ClassicClockView.kt и ресурсный файл атрибутов attrs.xml.

В приложении реализовано дополнительное задание, можно изменять цвет, ширину и длинну каждой стрелки.
Для этого в макете реализованы следующие аттрибуты:
<attr name="hourHandColor" format="color" />
<attr name="minuteHandColor" format="color" />
<attr name="secondHandColor" format="color" />
<attr name="hourHandLengthSize" format="float" />
<attr name="minuteHandLengthSize" format="float" />
<attr name="secondHandLengthSize" format="float" />
<attr name="hourHandWidthSize" format="float" />
<attr name="minuteHandWidthSize" format="float" />
<attr name="secondHandWidthSize" format="float" />

Пример и рекомендации для применения аттрибутов:
app:secondHandWidthSize="0.08" - ширина стрелки, задается значение типа float, рекомендуемые значения от 0,01 и до 1;
app:secondHandLengthSize="0.7" - длина стрелки, задается значение типа float, рекомендуемые значения от 0,1 и до 1; 
app:secondHandColor="@color/purple_200" - цвет стрелки, задается цвет из предустановленных цветов.
