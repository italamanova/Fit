package com.example.ira.fit.Helper;

public class LogReader {

/*//	private static LowPassFilterLinearAccel filter;

    private static Sampler parse(int number, String s) {
        Sampler c = null;
        float ax = 0, ay = 0, az = 0;
        long time = 0;
//        long curTime = System.currentTimeMillis();
//System.out.println("curTime="+curTime);
//Pattern p = Pattern.compile("Time: (.*?) :: X : (.*?) : Y : (.*?) : Z : (.*?)");
//Matcher m = p.matcher(s);

//while(m.find()) {
//    System.out.println(m.group(1));
//}
        int indT = s.indexOf("Time: ");
        int indX = s.indexOf(" :: X: ");
        int indY = s.indexOf(" Y: ");
        int indZ = s.indexOf(" Z: ");
        int indE = s.length();
//System.out.println("intT="+indT+";intX="+indX+";indY="+indY+";indZ="+indZ+";indE="+indE);
        String sT = s.substring(indT + 6, indX);
        String sX = s.substring(indX + 7, indY);
        String sY = s.substring(indY + 4, indZ);
        String sZ = s.substring(indZ + 4, indE);
//System.out.println("sT=|"+sT+"|;sX=|"+sX+"|;sY=|"+sY+"|;sZ=|"+sZ+"|");
        time = Long.parseLong(sT, 10);
        ax = Float.parseFloat(sX);
        ay = Float.parseFloat(sY);
        az = Float.parseFloat(sZ);
        c = new Sampler(ax, ay, az, "......");
        c.setTime(time);
        c.setNumber(number);
        return c;
    }

    *//*
        private static Sampler parseF(int number, String s)
        {
            Sampler c = null;
            float ax = 0, ay = 0, az = 0;
            long time = 0;
            int indT = s.indexOf("Time: ");
            int indX = s.indexOf(" :: X: ");
            int indY = s.indexOf(" Y: ");
            int indZ = s.indexOf(" Z: ");
            int indE = s.length();
            String sT = s.substring(indT + 6, indX);
            String sX = s.substring(indX + 7, indY);
            String sY = s.substring(indY + 4, indZ);
            String sZ = s.substring(indZ + 4, indE);
            time = Long.parseLong(sT, 10);
            ax = Float.parseFloat(sX);
            ay = Float.parseFloat(sY);
            az = Float.parseFloat(sZ);
            float[] fi = {ax, ay, az};
            float[] fo = filter.addSamples(fi);
            c = new Sampler(fo[0], fo[1], fo[2], "filter");
            c.setTime(time);
            c.setNumber(number);
            return c;
        }
    *//*
    public static void main(String[] args) {
        *//*if (args.length < 1) {
            System.out.println("LogReader log_fle_name");
            return;
        }
//	filter = new LowPassFilterLinearAccel();
        FileReader fr = new FileReader(args[0]);
        String s = "";
        Sampler first = null;
        Sampler last = null;
        Sampler prev = null;

//	Sampler firstF = null;
//	Sampler lastF = null;
//	Sampler prevF = null;*//*
        int number = 0;
        LowPassFilter fX = new LowPassFilter();
        LowPassFilter fY = new LowPassFilter();
        LowPassFilter fZ = new LowPassFilter();
        Sampler.setXFilter(fX);
        Sampler.setYFilter(fY);
        Sampler.setZFilter(fZ);
        *//*while (s != null) {
            s = fr.readLine();
            if (s != null) {
//			System.out.println(s);
                Sampler next = parse(number, s);
                if (next != null)
                    next.setPrev(prev);
                if (prev != null)
                    prev.setNext(next);
                else
                    first = next;
                last = next;
                prev = next;
*//**//*
            Sampler nextF = parseF(number, s);
			if(nextF != null)
				nextF.setPrev(prevF);
			if(prevF != null)
				prevF.setNext(nextF);
			else
				firstF = nextF;
			lastF = nextF;
			prevF = nextF;
*//**//*
                number++;
//break;
            }
        }
        Sampler cur = first;*//*
//	Sampler curF = firstF;
        Extreme eX = new Extreme();
        Extreme eY = new Extreme();
        Extreme eZ = new Extreme();
        number = 0;
        //while (cur != null) {

//		System.out.println(cur);
        float rX = eX.add(cur.getAxF());
        if (rX != 0) {
            System.out.println("extremeX=" + rX + " at " + number);
            eX.clear();
        }
        float rY = eY.add(cur.getAyF());
        if (rY != 0) {
            System.out.println("extremeY=" + rY + " at " + number);
            eY.clear();
        }
        float rZ = eZ.add(cur.getAzF());
        if (rZ != 0) {
            System.out.println("extremeZ=" + rZ + " at " + number);
            eZ.clear();
        }
//		System.out.println(cur);
        //cur = cur.getNext();
//		curF = curF.getNext();
        number++;
        //}
        //fr.close();
    }*/
    public boolean count(Sampler cur) {

        boolean result = false;
        int number = 0;
        LowPassFilter fX = new LowPassFilter();
        LowPassFilter fY = new LowPassFilter();
        LowPassFilter fZ = new LowPassFilter();
        Sampler.setXFilter(fX);
        Sampler.setYFilter(fY);
        Sampler.setZFilter(fZ);

        Extreme eX = new Extreme();
        Extreme eY = new Extreme();
        Extreme eZ = new Extreme();
        number = 0;

        float rX = eX.add(cur.getAxF());
        if (rX != 0) {
            System.out.println("extremeX=" + rX + " at " + number);
            eX.clear();
        }
        float rY = eY.add(cur.getAyF());
        if (rY != 0) {
            System.out.println("extremeY=" + rY + " at " + number);
            eY.clear();
        }
        float rZ = eZ.add(cur.getAzF());
        if (rZ != 0) {
            System.out.println("extremeZ=" + rZ + " at " + number);
            eZ.clear();
        }

        number++;
        return result;

    }
}
