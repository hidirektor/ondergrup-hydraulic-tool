package me.t3sl4.hydraulic.Utils.Database.Model.HydraulicData;

import me.t3sl4.hydraulic.Utils.Database.Model.Tank.Tank;

import java.util.*;

public class HydraulicData {
    public int kampanaBoslukX;
    public int kilitPlatformMotorBosluk;
    public int kampanaBoslukY;
    public int valfBoslukX;
    public int valfBoslukYArka;
    public int valfBoslukYOn;
    public int kilitliBlokAraBoslukX;
    public int tekHizAraBoslukX;
    public int ciftHizAraBoslukX;
    public int kompanzasyonTekHizAraBoslukX;
    public int sogutmaAraBoslukX;
    public int sogutmaAraBoslukYkOn;
    public int sogutmaAraBoslukYkArka;
    public int kilitMotorKampanaBosluk;
    public int kilitMotorMotorBoslukX; //hidros kilit motor kampana ile tank dış ölçüsü ara boşluğu
    public int kilitMotorBoslukYOn;
    public int kilitMotorBoslukYArka;
    public int kayipLitre;
    public int valfXBoslukSogutma;

    //TODO
    //Yeni sistem değişkenleri

    public ArrayList<String> uniteTipiDegerleri = new ArrayList<>(); //Jsondan gelen değerler

    /*
    Classic Side
     */
    public Map<String, LinkedList<String>> motorMap = new HashMap<>();
    public Map<String, String> motorKampanaMap = new HashMap<>();
    public Map<String, String> motorYukseklikMap = new HashMap<>();

    public Map<String, LinkedList<String>> coolingMap = new HashMap<>();
    public Map<String, LinkedList<String>> hydraulicLockMap = new HashMap<>();
    public Map<String, LinkedList<String>> pumpMap = new HashMap<>();
    public Map<String, LinkedList<String>> compensationMap = new HashMap<>();
    public Map<String, LinkedList<String>> valveTypeMap = new HashMap<>();
    public Map<String, LinkedList<String>> lockMotorMap = new HashMap<>();
    public Map<String, LinkedList<String>> lockPumpMap = new HashMap<>();

    /*
    Power Pack Side
     */
    public Map<String, LinkedList<String>> motorVoltajMap = new HashMap<>();
    public Map<String, LinkedList<String>> uniteTipiMap = new HashMap<>();
    public Map<String, LinkedList<String>> motorGucuMap = new HashMap<>();
    public Map<String, LinkedList<String>> pompaPowerPackMap = new HashMap<>();
    public Map<String, LinkedList<String>> tankTipiMap = new HashMap<>();
    public Map<String, LinkedList<String>> tankKapasitesiMap = new HashMap<>();
    public Map<String, LinkedList<String>> platformTipiMap = new HashMap<>();
    public Map<String, LinkedList<String>> valfTipiMap = new HashMap<>();
    //Bitişi

    public List<Tank> inputTanks = new ArrayList<>();

    //Parça Listesi için değişkenler:
    public ArrayList<String> parcaListesiKampana2501k = new ArrayList<>();
    public ArrayList<String> parcaListesiKampana3001k = new ArrayList<>();
    public ArrayList<String> parcaListesiKampana3501k = new ArrayList<>();
    public ArrayList<String> parcaListesiKampana4001k = new ArrayList<>();
    public ArrayList<String> parcaListesiKampana2502k = new ArrayList<>();
    public ArrayList<String> parcaListesiKampana3002k = new ArrayList<>();
    public ArrayList<String> parcaListesiKampana3502k = new ArrayList<>();
    public ArrayList<String> parcaListesiKampana4002k = new ArrayList<>();

    public ArrayList<String> parcaListesiPompa95 = new ArrayList<>();
    public ArrayList<String> parcaListesiPompa119 = new ArrayList<>();
    public ArrayList<String> parcaListesiPompa14 = new ArrayList<>();
    public ArrayList<String> parcaListesiPompa146 = new ArrayList<>();
    public ArrayList<String> parcaListesiPompa168 = new ArrayList<>();
    public ArrayList<String> parcaListesiPompa192 = new ArrayList<>();
    public ArrayList<String> parcaListesiPompa229 = new ArrayList<>();
    public ArrayList<String> parcaListesiPompa281 = new ArrayList<>();
    public ArrayList<String> parcaListesiPompa288 = new ArrayList<>();
    public ArrayList<String> parcaListesiPompa333 = new ArrayList<>();
    public ArrayList<String> parcaListesiPompa379 = new ArrayList<>();
    public ArrayList<String> parcaListesiPompa426 = new ArrayList<>();
    public ArrayList<String> parcaListesiPompa455 = new ArrayList<>();
    public ArrayList<String> parcaListesiPompa494 = new ArrayList<>();
    public ArrayList<String> parcaListesiPompa561 = new ArrayList<>();

    public ArrayList<String> parcaListesiMotor202 = new ArrayList<>();
    public ArrayList<String> parcaListesiMotor3 = new ArrayList<>();
    public ArrayList<String> parcaListesiMotor4 = new ArrayList<>();
    public ArrayList<String> parcaListesiMotor55 = new ArrayList<>();
    public ArrayList<String> parcaListesiMotor55Kompakt = new ArrayList<>();
    public ArrayList<String> parcaListesiMotor75Kompakt = new ArrayList<>();
    public ArrayList<String> parcaListesiMotor11 = new ArrayList<>();
    public ArrayList<String> parcaListesiMotor11Kompakt = new ArrayList<>();
    public ArrayList<String> parcaListesiMotor15 = new ArrayList<>();
    public ArrayList<String> parcaListesiMotor185 = new ArrayList<>();
    public ArrayList<String> parcaListesiMotor22 = new ArrayList<>();
    public ArrayList<String> parcaListesiMotor37 = new ArrayList<>();

    public ArrayList<String> parcaListesiKaplin1PN28 = new ArrayList<>();
    public ArrayList<String> parcaListesiKaplin1PN38 = new ArrayList<>();
    public ArrayList<String> parcaListesiKaplin1PN42 = new ArrayList<>();
    public ArrayList<String> parcaListesiKaplin2PN28 = new ArrayList<>();
    public ArrayList<String> parcaListesiKaplin2PN38 = new ArrayList<>();
    public ArrayList<String> parcaListesiKaplin2PN42 = new ArrayList<>();

    public ArrayList<String> parcaListesiValfBloklariTekHiz = new ArrayList<>();
    public ArrayList<String> parcaListesiValfBloklariCiftHiz = new ArrayList<>();
    public ArrayList<String> parcaListesiValfBloklariKilitliBlok = new ArrayList<>();
    public ArrayList<String> parcaListesiValfBloklariKompanzasyon = new ArrayList<>();
    public ArrayList<String> parcaListesiSogutucu = new ArrayList<>();

    public ArrayList<String> parcaListesiBasincSalteri = new ArrayList<>();

    public ArrayList<String> parcaListesiStandart = new ArrayList<>();

    //PowerPack - Hidros Parça Listesi:
    public HashMap<String, HashMap<String, String>> hidros380Parca = new HashMap<>();
    public HashMap<String, HashMap<String, String>> hidros220Parca = new HashMap<>();
    public HashMap<String, HashMap<String, String>> hidrosPompaParca = new HashMap<>();
    public HashMap<String, HashMap<String, String>> hidrosPompaCivataParca = new HashMap<>();
    public HashMap<String, HashMap<String, String>> hidrosDikeyTankParca = new HashMap<>();
    public HashMap<String, HashMap<String, String>> hidrosYatayTankParca = new HashMap<>();
    public HashMap<String, HashMap<String, String>> hidrosDikeyCiftHizParca = new HashMap<>();
    public HashMap<String, HashMap<String, String>> hidrosDikeyTekHizParca = new HashMap<>();
    public HashMap<String, HashMap<String, String>> hidrosYatayCiftHizParca = new HashMap<>();
    public HashMap<String, HashMap<String, String>> hidrosYatayTekHizParca = new HashMap<>();
    public HashMap<String, HashMap<String, String>> hidrosDikeyCiftHizParcaESP = new HashMap<>();
    public HashMap<String, HashMap<String, String>> hidrosDikeyTekHizParcaESP = new HashMap<>();
    public HashMap<String, HashMap<String, String>> hidrosYatayCiftHizParcaESP = new HashMap<>();
    public HashMap<String, HashMap<String, String>> hidrosYatayTekHizParcaESP = new HashMap<>();
    public HashMap<String, HashMap<String, String>> hidrosDevirmeliParca = new HashMap<>();
    public HashMap<String, HashMap<String, String>> hidrosGenelParca = new HashMap<>();
    public HashMap<String, HashMap<String, String>> hidrosTamParca = new HashMap<>();
    public HashMap<String, HashMap<String, String>> hidrosTamParcaYatay = new HashMap<>();
    public HashMap<String, HashMap<String, String>> hidrosTamParcaDikey = new HashMap<>();
    public HashMap<String, HashMap<String, String>> hidrosTamParcaESPHaric = new HashMap<>();
    public HashMap<String, HashMap<String, String>> hidrosTamParcaOzelTekValf = new HashMap<>();

    //PowerPack - İthal Parçalar
    public HashMap<String, HashMap<String, String>> hidrosIthalParcaMotor380 = new HashMap<>();
    public HashMap<String, HashMap<String, String>> hidrosIthalParcaMotor220 = new HashMap<>();
    public HashMap<String, HashMap<String, String>> hidrosIthalParcaPompa = new HashMap<>();
    public HashMap<String, HashMap<String, String>> hidrosIthalParcaPompaCivata = new HashMap<>();
    public HashMap<String, HashMap<String, String>> hidrosIthalParcaTankDikey = new HashMap<>();
    public HashMap<String, HashMap<String, String>> hidrosIthalParcaTankYatay = new HashMap<>();

    public HydraulicData() {
        //TODO
        //data update
    }
}