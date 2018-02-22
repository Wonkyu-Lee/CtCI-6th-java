import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Ex_16_12_XmlEncoding {

    Map<String, Integer> codeMap = new HashMap<>();

    class TagNotFoundException extends Exception {
        public TagNotFoundException() {
            super("Tag not found!");
        }
    }

    interface Encodable {
        void encode(LinkedList<String> parts) throws TagNotFoundException;
    }

    class Entity implements Encodable {
        String name;
        Collection<Attribute> attributes = new LinkedList<>();
        Collection<Entity> children = new LinkedList<>();
        String value;

        public Entity(String name, String value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public void encode(LinkedList<String> parts) throws TagNotFoundException {
            final String codeEnd = codeMap.get("END").toString();

            if (!codeMap.containsKey(name))
                throw new TagNotFoundException();

            parts.add(codeMap.get(name).toString());

            if (!attributes.isEmpty()) {
                for (Attribute each : attributes) {
                    each.encode(parts);
                }

                parts.add(codeEnd);
            }

            if (!children.isEmpty()) {
                for (Entity each : children) {
                    each.encode(parts);
                }

                parts.add(codeEnd);
            }

            if (value != null && !value.isEmpty()) {
                parts.add(value);
                parts.add(codeEnd);
            }
        }

        public String encode() {
            StringBuilder sb = new StringBuilder();
            LinkedList<String> parts = new LinkedList<>();
            try {
                encode(parts);

                while (parts.size() > 1) {
                    String part = parts.removeFirst();
                    sb.append(part);
                    sb.append(" ");
                }

                if (parts.size() > 0) {
                    String part = parts.removeFirst();
                    sb.append(part);
                }

            } catch (TagNotFoundException e) {
                System.err.println(e.getMessage());
            }

            return sb.toString();
        }
    }

    class Attribute implements Encodable {
        String name;
        String value;

        public Attribute(String name, String value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public void encode(LinkedList<String> parts) throws TagNotFoundException {
            if (!codeMap.containsKey(name))
                throw new TagNotFoundException();

            parts.add(codeMap.get(name).toString());
            parts.add(value);
        }
    }

    Ex_16_12_XmlEncoding() {
        codeMap.put("END", 0);
        codeMap.put("family", 1);
        codeMap.put("person", 2);
        codeMap.put("firstName", 3);
        codeMap.put("lastName", 4);
        codeMap.put("state", 5);

        Entity family = new Entity("family", "");
        family.attributes.add(new Attribute("lastName", "McDowell"));
        family.attributes.add(new Attribute("state", "CA"));

        Entity person = new Entity("person", "Some Message");
        person.attributes.add(new Attribute("firstName", "Gayle"));
        family.children.add(person);

        System.out.println(family.encode());
    }

    public static void main(String[] args) {
        new Ex_16_12_XmlEncoding();
    }
}
