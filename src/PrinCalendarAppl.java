import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

public class PrinCalendarAppl {

	private static Locale locale = Locale.US;
	private static int columnWidth = 4;

	public static void main(String[] args) {
		int monthYear[];
		try {
			monthYear = getMonthYear(args);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}
		printCalendar(monthYear[0], monthYear[1],monthYear[2]);

	}

	private static void printCalendar(int month, int year, int fistDayOfWeek) {
		printTitle(month, year);
		printWeekDays(fistDayOfWeek);
		printDates(month, year,fistDayOfWeek);

	}

	private static void printDates(int month, int year, int fistDayOfWeek) {
		int firstColumn = getFirstColumn(month, year)-fistDayOfWeek%7+1;
		printDatesFromFirstColumn(firstColumn, month, year);

	}

	private static void printDatesFromFirstColumn(int firstColumn, int month, int year) {
		printOffset(firstColumn);
		int lastDayOfMonth = getLastDayOfMonth(month, year);
		for (int day = 1; day <= lastDayOfMonth; day++) {
			System.out.printf("%" + columnWidth + "d", day);
			firstColumn++;
			if (firstColumn == 8) {
				firstColumn = 1;
				System.out.println();
			}
		}

	}

	private static int getLastDayOfMonth(int month, int year) {
		YearMonth yearMonth = YearMonth.of(year, month);
		return yearMonth.lengthOfMonth();
	}

	private static void printOffset(int firstColumn) {
		int limit = (firstColumn - 1) * columnWidth;

		System.out.print(" ".repeat(limit));

	}

	private static int getFirstColumn(int month, int year) {
		LocalDate firstDate = LocalDate.of(year, month, 1);

		return DayOfWeek.from(firstDate).getValue();
	}

	private static void printWeekDays(int fistDayOfWeek) {
		System.out.printf("%" + columnWidth / 2 + "s", " ");
		for (int i=fistDayOfWeek-1;i<fistDayOfWeek+6;i++) {
			DayOfWeek weekDay = DayOfWeek.of(i%7+1);
			System.out.printf("%s ", weekDay.getDisplayName(TextStyle.SHORT, locale));
		}
		System.out.println();

	}

	private static void printTitle(int month, int year) {
		Month monthName = Month.of(month);
		System.out.printf("\t%s, %d\n", monthName.getDisplayName(TextStyle.FULL, locale), year);

	}

	private static int[] getMonthYear(String[] args) throws Exception {

		return args.length == 0 ? currentMonthYear() : monthYearByArgs(args);
	}

	private static int[] monthYearByArgs(String[] args) throws Exception {
		int month;
		try {
			month = Integer.parseInt(args[0]);
			if (month < 1 || month > 12) {
				throw new Exception(String.format("you have entered %d but month should be in range [1-12]", month));
			}
		} catch (NumberFormatException e) {
			throw new Exception(String.format("you have entered %s but month value should be number", args[0]));
		}
		int year;
		try {
			year = args.length > 1 ? Integer.parseInt(args[1]) : LocalDate.now().getYear();
			if (year < 0) {
				throw new Exception("year should be only positive value");
			}
		} catch (NumberFormatException e) {
			throw new Exception(
					String.format("you have entered %s but year value should be a positive number", args[1]));
		}
		int weekDay;
		try {
			weekDay = args.length > 2 ? DayOfWeek.valueOf(args[2].toUpperCase()).getValue() : 1;
		}
		catch (Exception e) {
			String text="[";
			for (DayOfWeek weekd : DayOfWeek.values()) {
				text+=weekd+",";
			}
			throw new Exception(
					String.format("Week day shold be any of this strings: %s]", text));
		}
		return new int[] { month, year,weekDay  };
	}

	private static int[] currentMonthYear() {
		LocalDate current = LocalDate.now();
		return new int[] { current.getMonthValue(), current.getYear(),1 };
	}

}